package exceptions;

public class NotImplementedYetException extends RuntimeException {
    public NotImplementedYetException() {
        super();
    }

    public NotImplementedYetException(String errorMessage) {
        super(errorMessage);
    }
}
