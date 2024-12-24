import mediateque.mediatheque;
import server.Serveur;
import service.EmpruntRetour;
import service.Reservation;

import java.io.IOException;

public class AppliServer {

    public static void main(String[] args) throws IOException {

        new mediatheque();

        new Thread(new Serveur(Reservation.class,3000)).start();
        new Thread(new Serveur(EmpruntRetour.class,4000)).start();
    }
}
