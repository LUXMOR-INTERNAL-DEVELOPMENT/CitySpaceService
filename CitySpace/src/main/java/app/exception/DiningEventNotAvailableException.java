package app.exception;

public class DiningEventNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public DiningEventNotAvailableException(String message) {
    	  super(message);
    }

}
