package api.Dao;

import api.entity.Auth;

public interface AuthDao {

    String register(Auth auth);

    String verifyEmail(String email, String otp);

    String login(String email, String password);

    String verifyLoginOtp(String email, String otp);
}