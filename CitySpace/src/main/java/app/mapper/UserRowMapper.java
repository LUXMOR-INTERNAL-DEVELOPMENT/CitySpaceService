package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.User;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setId(rs.getString("id"));
        user.setStatus(rs.getString("status"));

        user.setCreatedAt(
                rs.getTimestamp("created_at") != null
                        ? rs.getTimestamp("created_at").toLocalDateTime()
                        : null
        );

        user.setCreatedBy(rs.getString("created_by"));

        user.setUpdatedAt(
                rs.getTimestamp("updated_at") != null
                        ? rs.getTimestamp("updated_at").toLocalDateTime()
                        : null
        );

        user.setUpdatedBy(rs.getString("updated_by"));

        return user;
    }
}
