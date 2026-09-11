package app.Exception;

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
	
	
}
