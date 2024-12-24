package service;

import document.Abonne;
import document.Cd;
import document.Document;
import document.IDocument;
import exception.EmpruntException;
import mediateque.mediatheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class EmpruntRetour extends server.Service {

    private Document document;
    private Abonne abonne;
    private Socket socket;

    public EmpruntRetour(Socket socket) {
        super(socket);
        this.socket = socket;
    }



    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true); // true pour activer l'auto-flush

            String numeroAbonne;
            String numeroDocument;

            while (true) {
                out.println("1 - Retourner | 2 - Emprunter");
                out.flush();

                String choix = in.readLine();
                if (choix.equalsIgnoreCase("quit")) {
                    out.println("Au revoir !");
                    break;
                }
                if (choix.equals("1")) {
                    out.println("Entrez l'ID du document : ");
                    out.flush();
                    numeroDocument = in.readLine();

                    document = mediatheque.chercherDocument(Integer.parseInt(numeroDocument));
                    try {
                        document.retour();
                        out.println("Document retourné avec succès.");
                        out.flush();
                        break;
                    } catch (EmpruntException e) {
                        out.println(e.getMessage());
                        out.flush();
                    }

                } else if (choix.equals("2")) {
                    out.println("Entrez l'ID de l'abonné : ");
                    out.flush();
                    numeroAbonne = in.readLine();

                    abonne = mediatheque.chercherAbonne(Integer.parseInt(numeroAbonne));

                    out.println("Entrez l'ID du document : ");
                    out.flush();
                    numeroDocument = in.readLine();

                    document = mediatheque.chercherDocument(Integer.parseInt(numeroDocument));

                    try {
                        if (document instanceof Cd && ((Cd) document).isAdulte()) {
                            int age = java.time.Year.now().getValue() - Integer.parseInt(abonne.getDateNaissance().toString().substring(0, 4));
                            if (age < 16) {
                                throw new EmpruntException("Vous n'avez pas l'âge requis pour emprunter ce document");
                            }
                        }

                        document.empruntPar(abonne);
                        out.println("Document emprunté avec succès.");
                        out.flush();
                        break;
                    } catch (EmpruntException e) {
                        out.println(e.getMessage());
                        out.flush();
                    }

                } else {
                    if (choix.equalsIgnoreCase("quit")) {
                        out.println("Au revoir !");
                        break;
                    }
                    out.println("Option invalide. Veuillez choisir à nouveau.");
                    out.flush();
                }
            }

        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
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
