package NeoBivago.exceptions;

public class InvalidAttributeException extends RuntimeException {
    
    public InvalidAttributeException(String message) {
        super(message);
    }
    
    public InvalidAttributeException() {
        super("InvalidAttributeException.");
    }

    private static final long serialVersionUID = 1L;
}
