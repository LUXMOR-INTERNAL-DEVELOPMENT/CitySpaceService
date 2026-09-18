package app.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceimpl {

    private final JavaMailSender mailSender;

    public EmailServiceimpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("CitySpace Email Verification OTP");
        message.setText("Your CitySpace OTP is: " + otp
                + "\n\nPlease use this OTP to verify your email.");

        mailSender.send(message);
    }
}