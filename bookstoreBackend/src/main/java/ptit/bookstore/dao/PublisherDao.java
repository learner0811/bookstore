package ptit.bookstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Address;
import ptit.bookstore.model.Publisher;

@Repository
public class PublisherDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Publisher> getAllPublisher()
	{
		String sql = "select * "
				+ "from publisher left join address "
				+ "on publisher.idAddress = address.id";
		List<Publisher> result = jdbcTemplate.query(sql, new PublisherMapper());
		return result;
		
	}
	
	public Publisher getPublisherById(int id)
	{
		String sql = "select * from publisher "
				+ "left join address "
				+ "on publisher.idAddress = address.id "
				+ "where publisher.id = ?";
		Publisher result = jdbcTemplate.queryForObject(sql, new Object[]{id}, new PublisherMapper());
		return result;
	}
	
	private class PublisherMapper implements RowMapper<Publisher>
	{

		public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
			Publisher result = new Publisher();
			result.setId(rs.getInt("publisher.id"));
			result.setName(rs.getString("publisher.name"));
			Address address = new Address();
			address.setId(rs.getInt("address.id"));
			address.setCity(rs.getString("address.city"));
			address.setCountry(rs.getString("address.country"));
			address.setDistrict(rs.getString("address.district"));
			address.setNumber(rs.getString("address.number"));
			result.setAddress(address);
			return result;
		}
	}
}
