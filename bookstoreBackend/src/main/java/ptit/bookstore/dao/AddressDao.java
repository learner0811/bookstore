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

import ptit.bookstore.model.Address;

@Repository
public class AddressDao {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	public Address findOne(int id) {
		PreparedStatement ps = null;
		Address address = new Address();
		address.setId(id);
		Connection connection = null;
		try {
			String sql = "select * from address where id = ?";
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				address.setCity(rs.getString("city"));
				address.setNumber(rs.getString("number"));
				address.setDistrict(rs.getString("district"));
				address.setCountry(rs.getString("country"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ps.close();
				connection.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return address;
	}
	
	public Address Save(final Address address) throws SQLException {		
		int autoGreneratedId = 0;
		KeyHolder holder = new GeneratedKeyHolder();		
		int rowAffect = jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "insert into address (number, district, city, country) values(?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);;
				ps.setString(1, address.getNumber());
				ps.setString(2, address.getDistrict());
				ps.setString(3, address.getCity());
				ps.setString(4, address.getCountry());				
				return ps;
			}
		}, holder);
				
		System.out.println("row affect in address dao save " + rowAffect);		
		autoGreneratedId = holder.getKey().intValue();
		if (autoGreneratedId == 0)
			return new Address();
		address.setId(autoGreneratedId);
		return address;
	}	
}
