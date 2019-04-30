package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
				user.setName(rs.getString("name"));
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
	
	public User getUserById(int userId) {
		PreparedStatement ps = null;
		User user = new User();
		Address address = new Address();
		user.setAddress(address);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from user "
					+ "left join address on user.idAddress = address.id "
					+ "left join account on user.idAccount = account.id "
					+ "where idClient = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("idClient"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				Account a = new Account();
				a.setId(rs.getInt("account.id"));
				a.setUsername(rs.getString("account.username"));
				a.setPassword(rs.getString("account.password"));
				user.setAccount(a);
				address.setCity(rs.getString("city"));
				address.setCountry(rs.getString("country"));
				address.setDistrict(rs.getString("district"));
				address.setId(rs.getInt("address.id"));
				address.setNumber(rs.getString("number"));
			}
			connection.close();
			return user;
		} catch (SQLException ex) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
		}
		return null;
	}

	public boolean changeInfo(final User user) {
		/*int affected = jdbcTemplate.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "update "
						+ "("
						+ "select * from user "
						+ "left join address on user.idAddress = address.id "
						+ "left join account on user.idAccount = account.id"
						+ ") as temp"
						+ "set temp.email = ?, temp.role = ?, temp.name = ?, "
						+ "temp.number = ?, temp.district = ?, temp.city = ?, temp.country = ?, "
						+ "temp.username = ?, temp.password = ? "
						+ "where temp.idClient = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getRole());
				ps.setString(3, user.getName());
				
				ps.setString(4, user.getAddress().getNumber());
				ps.setString(5, user.getAddress().getDistrict());
				ps.setString(6, user.getAddress().getCity());
				ps.setString(7, user.getAddress().getCountry());
				
				ps.setString(8, user.getAccount().getUsername());
				ps.setString(9, user.getAccount().getPassword());
				
				ps.setInt(10, user.getId());
				
				return ps;
			}
		});
		System.out.println(affected);*/
		int affected1 = jdbcTemplate.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "update user set user.email = ?, user.role = ?, user.name = ? where user.idClient = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getRole());
				ps.setString(3, user.getName());
				ps.setInt(4, user.getId());
				return ps;
			}
		});
		int affected2 = jdbcTemplate.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "update address set address.number = ?, address.district = ?, address.city = ?, address.country = ? where address.id = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user.getAddress().getNumber());
				ps.setString(2, user.getAddress().getDistrict());
				ps.setString(3, user.getAddress().getCity());
				ps.setString(4, user.getAddress().getCountry());
				ps.setInt(5, user.getAddress().getId());
				return ps;
			}
		});
		int affected3 = jdbcTemplate.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "update account set account.username = ?, account.password = ? where account.id = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, user.getAccount().getUsername());
				ps.setString(2, user.getAccount().getPassword());
				
				ps.setInt(3, user.getAccount().getId());
				return ps;
			}
		});
		return affected1 + affected2 + affected3 > 0 ? true : false;
	}
}
