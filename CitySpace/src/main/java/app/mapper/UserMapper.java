package app.mapper;

import org.springframework.stereotype.Component;


import app.dto.UserUpdateProfileRequest;
import app.entity.User;




@Component
public class UserMapper {
	 public void updateUserFromDto(
	            User existingUser,
	            UserUpdateProfileRequest dto) {

	        if (dto.getUser_name() != null) {
	            existingUser.setUserName(dto.getUser_name());
	        }

	        if (dto.getEmail() != null) {
	            existingUser.setEmail(dto.getEmail());
	        }

	        if (dto.getPhoneno() != null) {
	            existingUser.setPhoneno(dto.getPhoneno());
	        }

	        if (dto.getPassword() != null) {
	            existingUser.setPassword(dto.getPassword());
	        }
	    }
}
