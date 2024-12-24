package document;

import exception.EmpruntException;

import java.util.Date;

public class Abonne {
    private int numero;
    private String nom;
    private Date dateNaissance;

    public Abonne(int numero, String nom, Date dateNaissance) {
        this.numero = numero;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }


    public void reserver(Document document) throws EmpruntException {
        document.reservationPour(this);
    }

    public void emprunter(Document document) throws EmpruntException {
        document.empruntPar(this);
    }

    public void retour(Document document) throws EmpruntException {
        document.retour();
    }


}

