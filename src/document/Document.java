package document;

import exception.EmpruntException;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public abstract class Document {

    private Abonne emprunteur;
    private Abonne reserveur = null;
    private final java.util.concurrent.ScheduledExecutorService scheduler = java.util.concurrent.Executors.newScheduledThreadPool(1);
    private final Object lock = new Object();

    public Abonne emprunteur() {
        return this.emprunteur;
    }

    public Abonne reserveur() {
        return this.reserveur;
    }


    public void reservationPour(Abonne abonne) throws EmpruntException {
        synchronized(lock) {
            if (emprunteur != null) {
                throw new EmpruntException("le document est déjà emprunté");
            } else if (reserveur != null) {
                throw new EmpruntException("le document est déjà réservé jusqu'à " + LocalTime.now().plusHours(2).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
            } else {

                this.reserveur = abonne;
                scheduler.schedule(() -> {
                    if (this.reserveur == abonne && this.emprunteur == null) {
                        this.reserveur = null;
                        System.out.println("Réservation annulée");
                    }
                }, 2, TimeUnit.HOURS);
            }
        }
    }

    public void empruntPar(Abonne abonne) throws EmpruntException {
        synchronized(lock) {
            if (emprunteur != null) {
                throw new EmpruntException("Le document est déjà emprunté.");
            } else if (reserveur == null) {
                this.emprunteur = abonne;
                return;
            } else if (reserveur != null && reserveur != abonne) {
                throw new EmpruntException("Le document est déjà réservé.");
            } else {
                scheduler.shutdown();
                this.emprunteur = abonne;
                this.reserveur = null;
            }
        }
    }

    public void retour() throws EmpruntException {
        synchronized(lock) {
            if (this.emprunteur == null) {
                throw new EmpruntException("Le document n'est pas emprunté ni réservé.");
            } else {
                this.emprunteur = null;
            }
        }
    }


}