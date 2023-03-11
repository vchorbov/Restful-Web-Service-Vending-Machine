package chorbova.velichka.restful.web.service.exceptions;

public class MissingValuesException extends Exception {
    public MissingValuesException() {
        super("Some value(s) are missing in the request. Kindly refer to the manual.");
    }

    public MissingValuesException(String message) {
        super(message);
    }

    public MissingValuesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingValuesException(Throwable cause) {
        super(cause);
    }
}
