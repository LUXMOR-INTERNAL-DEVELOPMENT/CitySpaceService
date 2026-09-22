package app.exception;

public class TableNotAvailableException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	public TableNotAvailableException(String message) {
    	 super(message);
    }
}
