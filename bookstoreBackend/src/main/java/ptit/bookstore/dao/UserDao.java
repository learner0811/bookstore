package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Account;
import ptit.bookstore.model.Address;
import ptit.bookstore.model.User;

@Repository
public class UserDao {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;	

	public User findUserByAccount(Account account) {
		PreparedStatement ps = null;
		User user = new User();
		Address address = new Address();
		user.setAddress(address);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from user where idAccount = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, account.getId());
			ResultSet rs = ps.executeQuery();
			// phai set address = cach dung AddressDao
			while (rs.next()) {
				user.setId(rs.getInt("idClient"));
				user.setAccount(account);
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				address.setId(rs.getInt("idAddress"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return user;
	}
	
	public User Save(final User user) throws SQLException {		
		int autoGreneratedId = 0;
		
		KeyHolder holder = new GeneratedKeyHolder();
		int affectRow = jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "insert into user (idAddress, idAccount, email, role, name) values(?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setInt(1, user.getAddress().getId());
				ps.setInt(2, user.getAccount().getId());
				ps.setString(3, user.getEmail());
				ps.setString(4, user.getRole());
				ps.setString(5, user.getName());
				return ps;
			}
		}, holder);
		System.out.println("row affect in save user dao save functoin " + affectRow);
		autoGreneratedId = holder.getKey().intValue();		
		user.setId(autoGreneratedId);
		return user;
	}	

}
