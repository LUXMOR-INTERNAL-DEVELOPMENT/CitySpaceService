package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.entity.User;
import app.mapper.UserRowMapper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Value("${admin.users.getAll}")
    private String getAllUsersQuery;

    // Get All Users
    public List<User> getAllUsers() {

        return jdbcTemplate.query(
                getAllUsersQuery,
                userRowMapper
        );
    }

	public User getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}