package app.Exception;

public class EventNotAvailableException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public EventNotAvailableException(String message) {
			super(message);
		}
    
}
