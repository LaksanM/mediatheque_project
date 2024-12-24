package service;

import document.Abonne;
import document.Cd;
import document.Document;
import document.Livre;
import exception.EmpruntException;
import mediateque.mediatheque;
import server.Service;

import javax.print.Doc;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class Reservation extends Service {

    private Abonne abonne;
    private Document document;

    public Reservation(Socket socket) {
        super(socket);
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);

            boolean running = true;

            while (running) {

                out.println("Entrez le numéro de l'abonné : ");

                String numeroAbonne = in.readLine();

                if (numeroAbonne.equalsIgnoreCase("quit")) {
                    out.println("Au revoir !");
                    break;
                }

                Abonne abonne = mediatheque.chercherAbonne(Integer.parseInt(numeroAbonne));

                if (abonne != null) {
                    out.println("Entrez le numéro du document : ");
                    String numeroDocument = in.readLine();

                    if (numeroDocument.equalsIgnoreCase("quit")) {
                        out.println("Au revoir !");
                        break;
                    }

                    Document document = mediatheque.chercherDocument(Integer.parseInt(numeroDocument));

                    if (document != null) {
                        try {
                             if (document instanceof Cd && ((Cd) document).isAdulte()) {
                                 int age = java.time.Year.now().getValue() - Integer.parseInt(abonne.getDateNaissance().toString().substring(0, 4));
                                 if (age < 16) {
                                     throw new EmpruntException("Vous n'avez pas l'âge requis pour emprunter ce document");
                                 }
                             }
                            abonne.reserver(document);
                            out.println("Document réservé avec succès.");
                            running = false;
                        } catch (EmpruntException e) {
                            out.println("Impossible de réserver le document : " + e.getMessage());
                        }
                    } else {
                        out.println("Document non trouvé.");
                    }
                } else {
                    out.println("Abonné non trouvé.");
                }
            }

            in.close();
            out.close();
            getSocket().close();

        } catch (SocketException e) {
            out.println("SocketException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getSocket() != null) {
                    getSocket().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
