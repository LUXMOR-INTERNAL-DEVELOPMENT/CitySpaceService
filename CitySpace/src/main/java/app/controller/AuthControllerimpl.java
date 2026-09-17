package api.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.Service.AuthService;
import api.entity.Auth;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerimpl {

    private final AuthService authService;

    public AuthControllerimpl(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Auth auth) {
        return authService.register(auth);
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email,
                              @RequestParam String otp) {
        return authService.verifyEmail(email, otp);
    }

    @PostMapping("/login")
    public String login(@RequestBody Auth auth) {
        return authService.login(
                auth.getEmail(),
                auth.getPassword()
        );
    }

    @PostMapping("/verify-login-otp")
    public String verifyLoginOtp(@RequestParam String email,
                                 @RequestParam String otp) {
        return authService.verifyLoginOtp(email, otp);
    }
}