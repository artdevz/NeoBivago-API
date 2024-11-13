package NeoBivago.exceptions;

public class CapacityExceededException extends RuntimeException {
    
    public CapacityExceededException(String message) {
        super(message);
    }

    public CapacityExceededException() {
        super("CapacityExceededException.");
    }

    private static final long serialVersionUID = 1L;

}
