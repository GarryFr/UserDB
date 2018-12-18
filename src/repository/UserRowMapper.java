package repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import domain.User;

public final class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet result, int rowNum) throws SQLException {
		User user = new User();
		user.setId(result.getLong("id"));
		user.setName(result.getString("username"));
		return user;
	}

}
