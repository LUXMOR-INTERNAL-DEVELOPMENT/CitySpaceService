package app.Service;

import org.springframework.stereotype.Service;

import app.Dao.AuthDao;
import app.entity.Auth;

@Service
public class AuthServiceimpl implements AuthService {

    private final AuthDao authDao;

    public AuthServiceimpl(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public String register(Auth auth) {
        return authDao.register(auth);
    }

    @Override
    public String verifyEmail(String email, String otp) {
        return authDao.verifyEmail(email, otp);
    }

    @Override
    public String login(String email, String password) {
        return authDao.login(email, password);
    }

    @Override
    public String verifyLoginOtp(String email, String otp) {
        return authDao.verifyLoginOtp(email, otp);
    }
}