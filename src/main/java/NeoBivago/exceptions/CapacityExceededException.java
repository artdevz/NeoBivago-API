package NeoBivago.exceptions;

public class CapacityExceededException extends RuntimeException {
    
    public CapacityExceededException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;

}
