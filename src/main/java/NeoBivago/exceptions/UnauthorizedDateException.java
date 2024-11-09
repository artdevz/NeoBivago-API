package NeoBivago.exceptions;

public class UnauthorizedDateException extends RuntimeException {
    
    public UnauthorizedDateException(String message) {
        super(message);
    }

    public UnauthorizedDateException() {
        super("UnauthorizedDateException");
    }

    private static final long serialVersionUID = 1L;

}
