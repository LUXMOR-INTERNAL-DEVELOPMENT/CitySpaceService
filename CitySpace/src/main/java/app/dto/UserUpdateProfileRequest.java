package app.dto;

import lombok.Data;

@Data
public class UserUpdateProfileRequest {

	private String user_name;
	private String email;
	private String password;
	private Long phoneno;
	
}
