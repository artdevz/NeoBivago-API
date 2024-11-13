package NeoBivago.exceptions;

public class ExistingAttributeException extends RuntimeException {
    
    public ExistingAttributeException(String message) {
        super(message);
    }

    public ExistingAttributeException() {
        super("ExistingAttributeException.");
    }

    private static final long serialVersionUID = 1L;

}
