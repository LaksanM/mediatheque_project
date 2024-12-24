package exception;

import document.Abonne;

public class EmpruntException extends Exception {
    private final Abonne empruntId;

    public EmpruntException(String message) {
        super(message);
        this.empruntId = null;
    }
}