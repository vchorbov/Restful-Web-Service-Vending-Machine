package chorbova.velichka.restful.web.service.exceptions;

public class NotSupportedCoinTypeException extends Exception{
    public NotSupportedCoinTypeException() {
    }

    public NotSupportedCoinTypeException(String message) {
        super(message);
    }

    public NotSupportedCoinTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedCoinTypeException(Throwable cause) {
        super(cause);
    }
}
