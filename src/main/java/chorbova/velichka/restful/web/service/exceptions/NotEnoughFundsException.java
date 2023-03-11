package chorbova.velichka.restful.web.service.exceptions;

public class NotEnoughFundsException extends Exception {
    public NotEnoughFundsException() {
        super("The Balance is not enough to purchase this item. Please insert more coins.");
    }

    public NotEnoughFundsException(String message) {
        super(message);
    }

    public NotEnoughFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughFundsException(Throwable cause) {
        super(cause);
    }
}
