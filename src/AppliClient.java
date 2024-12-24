import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClient {

    public static void main(String[] args) {
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = null;
        BufferedReader input = null;
        PrintWriter output = null;

        try {
            System.out.println("Choisissez une option :");
            System.out.println("1 - Réserver");
            System.out.println("2 - Emprunter");

            String userChoice = consoleInput.readLine();
            int port;

            switch (userChoice) {
                case "1":
                    port = 3000;
                    break;
                case "2":
                    port = 4000;
                    break;
                default:
                    System.out.println("Choix non valide. Fin du programme.");
                    return;
            }

            socket = new Socket("localhost", port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage;
            while ((serverMessage = input.readLine()) != null) {
                System.out.println(serverMessage);

                if ("Au revoir !".equals(serverMessage) || "Document réservé avec succès.".equals(serverMessage) || "Document emprunté avec succès.".equals(serverMessage) || "Document retourné avec succès.".equals(serverMessage)) {
                    break;
                }

                System.out.print("Votre réponse: ");
                String userResponse = consoleInput.readLine();
                output.println(userResponse);
            }

            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
