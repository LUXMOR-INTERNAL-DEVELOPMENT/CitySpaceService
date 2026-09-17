package api.Dao;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import api.entity.Auth;

@Repository
public class AuthDaoimpl implements AuthDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${auth.getByEmail}")
    private String getByEmailQuery;

    @Value("${auth.updateOtp}")
    private String updateOtpQuery;

    @Value("${auth.verifyEmail}")
    private String verifyEmailQuery;

    public AuthDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String register(Auth auth) {

        List<Auth> users = jdbcTemplate.query(
                getByEmailQuery,
                (rs, rowNum) -> {

                    Auth a = new Auth();

                    a.setId(rs.getLong("id"));
                    a.setName(rs.getString("name"));
                    a.setEmail(rs.getString("email"));
                    a.setPhone(rs.getString("phone"));
                    a.setPassword(rs.getString("password"));
                    a.setRole(rs.getString("role"));
                    a.setOtp(rs.getString("otp"));
                    a.setEmailVerified(
                            rs.getBoolean("email_verified")
                    );
                    a.setStatus(rs.getString("status"));

                    return a;
                },
                auth.getEmail()
        );

        if (!users.isEmpty()) {
            return "Email already registered";
        }

        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        auth.setOtp(otp);
        auth.setEmailVerified(false);

        if ("VENDOR".equalsIgnoreCase(auth.getRole())) {
            auth.setStatus("PENDING");
        } else {
            auth.setStatus("APPROVED");
        }

        String insertQuery =
                "INSERT INTO auth " +
                "(name, email, phone, password, role, otp, email_verified, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                insertQuery,
                auth.getName(),
                auth.getEmail(),
                auth.getPhone(),
                auth.getPassword(),
                auth.getRole(),
                auth.getOtp(),
                auth.isEmailVerified(),
                auth.getStatus()
        );

        return "Registration successful. OTP: " + otp;
    }

    @Override
    public String verifyEmail(String email, String otp) {

        String query =
                "SELECT otp FROM auth WHERE email=?";

        List<String> otps = jdbcTemplate.query(
                query,
                (rs, rowNum) -> rs.getString("otp"),
                email
        );

        if (otps.isEmpty()) {
            return "Email not found";
        }

        if (!otp.equals(otps.get(0))) {
            return "Invalid OTP";
        }

        jdbcTemplate.update(
                verifyEmailQuery,
                true,
                email
        );

        return "Email verified successfully";
    }

    @Override
    public String login(String email, String password) {

        String query =
                "SELECT password, email_verified, status " +
                "FROM auth WHERE email=?";

        List<Auth> users = jdbcTemplate.query(
                query,
                (rs, rowNum) -> {

                    Auth a = new Auth();

                    a.setPassword(rs.getString("password"));
                    a.setEmailVerified(
                            rs.getBoolean("email_verified")
                    );
                    a.setStatus(rs.getString("status"));

                    return a;
                },
                email
        );

        if (users.isEmpty()) {
            return "Email not found";
        }

        Auth auth = users.get(0);

        if (!password.equals(auth.getPassword())) {
            return "Invalid password";
        }

        if (!auth.isEmailVerified()) {
            return "Please verify your email first";
        }

        if ("PENDING".equalsIgnoreCase(auth.getStatus())) {
            return "Vendor approval pending";
        }

        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        jdbcTemplate.update(
                updateOtpQuery,
                otp,
                email
        );

        return "Login OTP sent. OTP: " + otp;
    }

    @Override
    public String verifyLoginOtp(String email, String otp) {

        String query =
                "SELECT otp, role FROM auth WHERE email=?";

        List<Auth> users = jdbcTemplate.query(
                query,
                (rs, rowNum) -> {

                    Auth a = new Auth();

                    a.setOtp(rs.getString("otp"));
                    a.setRole(rs.getString("role"));

                    return a;
                },
                email
        );

        if (users.isEmpty()) {
            return "Email not found";
        }

        Auth auth = users.get(0);

        if (!otp.equals(auth.getOtp())) {
            return "Invalid OTP";
        }

        return "Login successful. Role: " + auth.getRole();
    }
}