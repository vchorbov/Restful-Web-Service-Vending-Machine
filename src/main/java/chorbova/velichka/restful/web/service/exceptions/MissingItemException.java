package chorbova.velichka.restful.web.service.exceptions;

public class MissingItemException extends Exception {

    public MissingItemException() {
        super("No Item was provided for the given operation!");
    }

    public MissingItemException(String message) {
        super(message);
    }

    public MissingItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingItemException(Throwable cause) {
        super(cause);
    }
}
