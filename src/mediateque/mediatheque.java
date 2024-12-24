package mediateque;

import document.Abonne;
import document.Cd;
import document.Document;
import document.Livre;
import exception.EmpruntException;
import service.EmpruntRetour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mediatheque {

    private static mediatheque instance = null;
    private static List<Abonne> abonnes = new ArrayList<>();
    private static List<Livre> livres = new ArrayList<>();
    private static List<Cd> cds = new ArrayList<>();

    private BaseDonnées bd;

    public mediatheque() throws IOException {
        bd = new BaseDonnées();
        bd.ReadAbonnee();
        bd.ReadCd();
        bd.ReadLivre();
        bd.close();
    }

    public static void ajouterAbonne(Abonne abonne) {
        abonnes.add(abonne);
    }

    public static List<Abonne> getAbonnes() {
        return abonnes;
    }

    public static void ajouterLivre(Livre livre) {
        livres.add(livre);
    }

    public static List<Livre> getLivres() {
        return livres;
    }

    public static void ajouterCd(Cd cd) {
        cds.add(cd);
    }

    public static List<Cd> getCds() {
        return cds;
    }


    public static Abonne chercherAbonne(int numeroAbonne) {
        for (Abonne abonne : abonnes) {
            if (abonne.getNumero() == numeroAbonne) {
                return abonne;
            }
        }
        return null;
    }

    public static Document chercherDocument(int numeroDocument) {
        for (Livre livre : livres) {
            if (livre.getDocumentID() == numeroDocument) {
                return livre;
            }
        }
        for (Cd cd : cds) {
            if (cd.getDocumentID() == numeroDocument) {
                return cd;
            }
        }


        return null;
    }

    public void retournerDocument(int numeroDocument) {
        Document document = chercherDocument(numeroDocument);
        try {
            document.retour();
        } catch (EmpruntException e) {
            e.printStackTrace();
        }
    }

}

