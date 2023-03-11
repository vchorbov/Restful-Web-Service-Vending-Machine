package chorbova.velichka.restful.web.service.exceptions;

public class ItemAlreadyPresentException extends Exception{
    public ItemAlreadyPresentException() {
        super("An Item of the same type is already present in the vending machine.");
    }

    public ItemAlreadyPresentException(String message) {
        super(message);
    }

    public ItemAlreadyPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemAlreadyPresentException(Throwable cause) {
        super(cause);
    }
}
