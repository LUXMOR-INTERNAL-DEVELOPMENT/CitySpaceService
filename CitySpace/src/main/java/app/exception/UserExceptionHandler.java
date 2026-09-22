package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(value=EventNotAvailableException.class)
	public ResponseEntity<?>eventNotFoundException(EventNotAvailableException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

	}


	@ExceptionHandler(value=UserIdNotFoundException.class)
	public ResponseEntity<?>userIdNotFoundException(UserIdNotFoundException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value=AccessDeniedException.class)
	public ResponseEntity<?>userIdNotFoundException(AccessDeniedException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value=DiningEventNotAvailableException.class)
	public ResponseEntity<?>diningEventNotAvailableException(DiningEventNotAvailableException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);

	}
	
	
	
	
	@ExceptionHandler(value=InvalidBookingDateException.class)
	public ResponseEntity<?>invalidBookingDateException(InvalidBookingDateException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value=SlotExpiredException.class)
	public ResponseEntity<?>slotExpiredException(SlotExpiredException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(value=TableNotAvailableException.class)
	public ResponseEntity<?>tableNotAvailableException(TableNotAvailableException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(value=InvalidGuestCountException.class)
	public ResponseEntity<?>InvalidGuestCountException(InvalidGuestCountException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);

	}
	@ExceptionHandler(value=VendorNotAvailableException.class)
	public ResponseEntity<?>vendorNotAvailableException(VendorNotAvailableException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(value=SlotNotAvailableException.class)
	public ResponseEntity<?>slotNotAvailableException(SlotNotAvailableException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);

	}
	


}
