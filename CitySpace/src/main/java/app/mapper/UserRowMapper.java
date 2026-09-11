package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.Entity.User;


@Component
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();

		user.setUserId(rs.getString("user_id"));

		user.setUserName(rs.getString("user_name"));

		user.setEmail(rs.getString("email"));

		user.setPassword(rs.getString("password"));

		user.setPhoneno(rs.getLong("phoneno"));

		user.setStatus(rs.getString("status"));

		user.setRole(rs.getString("role"));

		if (rs.getTimestamp("created_at") != null) {
		    user.setCreatedAt(
		        rs.getTimestamp("created_at").toLocalDateTime()
		    );
		}

		user.setCreatedBy(rs.getString("created_by"));

		if (rs.getTimestamp("updated_at") != null) {
		    user.setUpdatedAt(
		        rs.getTimestamp("updated_at").toLocalDateTime()
		    );
		}

		user.setUpdatedBy(rs.getString("updated_by"));

		return user;
	}

}
