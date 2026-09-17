package api.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import api.entity.User;

@Repository
public class UserDaoimpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${user.getUserById}")
    private String getUserByIdQuery;

    public UserDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(String userId) {

        List<User> users = jdbcTemplate.query(
                getUserByIdQuery,
                (rs, rowNum) -> {

                    User user = new User();

                    user.setId(rs.getLong("id"));
                    user.setUserId(rs.getString("user_id"));
                    user.setAuthId(rs.getLong("auth_id"));

                    if (rs.getTimestamp("created_at") != null) {
                        user.setCreatedAt(
                                rs.getTimestamp("created_at").toLocalDateTime()
                        );
                    }

                    if (rs.getTimestamp("updated_at") != null) {
                        user.setUpdatedAt(
                                rs.getTimestamp("updated_at").toLocalDateTime()
                        );
                    }

                    user.setUpdatedBy(rs.getString("updated_by"));

                    return user;
                },
                userId
        );

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }
}