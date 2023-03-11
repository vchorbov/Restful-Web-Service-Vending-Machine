package chorbova.velichka.restful.web.service.exceptions;

public class NotSupportedCoinTypeExceprion extends Exception{
    public NotSupportedCoinTypeExceprion() {
    }

    public NotSupportedCoinTypeExceprion(String message) {
        super(message);
    }

    public NotSupportedCoinTypeExceprion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedCoinTypeExceprion(Throwable cause) {
        super(cause);
    }
}
