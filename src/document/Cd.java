package document;

public class Cd extends Document {

    private int numeroID;
    private int documentID;
    private String titre;
    private boolean adulte;

    public Cd(int id, int docid , String titre, Boolean adulte) {
        this.numeroID = id;
        this.documentID = docid;
        this.titre = titre;
        this.adulte = adulte;
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

    public boolean isAdulte() {
        return adulte;
    }

}
