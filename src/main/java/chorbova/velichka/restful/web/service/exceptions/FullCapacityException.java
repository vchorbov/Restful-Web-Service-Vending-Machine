package chorbova.velichka.restful.web.service.exceptions;

public class FullCapacityException extends Exception {
    public FullCapacityException() {
        super("The Vending Machine's capacity for that item has been reached or exceeded.");
    }

    public FullCapacityException(String message) {
        super(message);
    }

    public FullCapacityException(String message, Throwable cause) {
        super(message, cause);
    }

    public FullCapacityException(Throwable cause) {
        super(cause);
    }
}
