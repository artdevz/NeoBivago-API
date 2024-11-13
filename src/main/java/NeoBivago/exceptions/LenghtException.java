package NeoBivago.exceptions;

public class LenghtException extends RuntimeException {
    
    public LenghtException(String message) {
        super(message);
    }

    public LenghtException() {
        super("LenghtException");
    }

    private static final long serialVersionUID = 1L;

}
