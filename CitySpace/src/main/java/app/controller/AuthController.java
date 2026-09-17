package api.Controller;

import org.springframework.web.bind.annotation.*;

import api.Service.AuthService;
import api.entity.Auth;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
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