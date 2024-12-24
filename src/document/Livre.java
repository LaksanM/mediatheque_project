package document;

public class Livre extends Document {

    private int numeroID;
    private int documentID;
    private String titre;
    private int nombrePages;

    public Livre(int id, int docid, String titre, int nombrePages) {
        this.numeroID = id;
        this.documentID = docid;
        this.titre = titre;
        this.nombrePages = nombrePages;
    }

    public int getNumeroID() {
        return numeroID;
    }

    public int getDocumentID() {
        return documentID;
    }

    public String getTitre() {
        return titre;
    }

    public int getNombrePages() {
        return nombrePages;
    }




}
