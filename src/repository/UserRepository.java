package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.tree.TreePath;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import domain.User;

public class UserRepository {
	
	static JdbcTemplate jdbcTemplateObj;
	static SimpleDriverDataSource dataSourceObj;

	// Database Configuration Parameters
	static String DB_USERNAME = "postgres";
	static String DB_PASSWORD = "197703";
	static String DB_URL = "jdbc:postgresql://localhost:5432/userdb";

	public static SimpleDriverDataSource getDatabaseConnection() throws SQLException {
		dataSourceObj = new SimpleDriverDataSource();
		dataSourceObj.setDriver(new org.postgresql.Driver());
		dataSourceObj.setUrl(DB_URL);
		dataSourceObj.setUsername(DB_USERNAME);
		dataSourceObj.setPassword(DB_PASSWORD);
		return dataSourceObj;
	}

	public JdbcTemplate createJdbcTemplateObj() {
	
		jdbcTemplateObj = null;
		try {
			jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jdbcTemplateObj;
	}
	
	@SuppressWarnings("rawtypes")
	public List<User> selectAll() {
		
		String sqlSelectAllQuery = "SELECT id, username FROM usertable";

		@SuppressWarnings("unchecked")
		List<User> userList = createJdbcTemplateObj().query(sqlSelectAllQuery, new RowMapper() {
			public User mapRow(ResultSet result, int rowNum) throws SQLException{
				User user = new User();
				user.setId(result.getLong("id"));
				user.setName(result.getString("username"));
				return user;
			}
		});

		return userList;
	}
	
	public void insert(User user) {
		
		String sqlInsert = "INSERT INTO usertable (id, username) VALUES (?, ?) ";
		createJdbcTemplateObj().update(sqlInsert, new Object[] {user.getId(), user.getName()});
	}
	
	public User selectById(Long id) {
		
		String sqlSelectById = "SELECT * FROM usertable WHERE id = ?";
		return createJdbcTemplateObj().queryForObject(sqlSelectById, new Object[] { id }, new UserRowMapper());
	}
	
	public void update(User user) {
	
		String sqlUpdate = "UPDATE usertable SET username = ? WHERE id = ?";
		createJdbcTemplateObj().update(sqlUpdate, user.getName(), user.getId());
	}

	public User selectByName(String name) {
		
		String sqlSelectById = "SELECT * FROM usertable WHERE username = ?";
		return createJdbcTemplateObj().queryForObject(sqlSelectById, new Object[] { name }, new UserRowMapper());
	}
	
	public boolean delete(Long id) {
	
		String sqlDelete = "DELETE FROM usertable WHERE id = ? ";
		return createJdbcTemplateObj().update(sqlDelete, id) > 0;
		
	}
	public User findFullname(String name) {
	
		String sqlfindFullname = "SELECT * FROM usertable WHERE username LIKE ?";

		return createJdbcTemplateObj().queryForObject(sqlfindFullname, new Object[] {"%"+name+"%" }, new UserRowMapper());
		
	}
	
}
