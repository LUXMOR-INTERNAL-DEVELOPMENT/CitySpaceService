package app.exception;

public class VendorNotAvailableException
        extends RuntimeException {

    public VendorNotAvailableException(String message) {
        super(message);
    }
}