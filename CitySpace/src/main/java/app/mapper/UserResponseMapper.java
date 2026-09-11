package app.mapper;

import org.springframework.stereotype.Component;

import app.Entity.User;
import app.dto.UserResponse;



@Component
public class UserResponseMapper {
   public UserResponse userResponse(User user) {
	   
	   UserResponse dto = new UserResponse();
	   dto.setName(user.getUserName());
	   dto.setEmail(user.getEmail());
	   dto.setPhoneno(user.getPhoneno());
	   dto.setStatus(user.getStatus());
	   return dto;
   }

   
}
