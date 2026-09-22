package app.exception;



public class SlotExpiredException extends RuntimeException {

    public SlotExpiredException(String message) {
        super(message);
    }
}
