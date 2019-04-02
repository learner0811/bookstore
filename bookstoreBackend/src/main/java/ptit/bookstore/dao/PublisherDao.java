package ptit.bookstore.dao;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Address;
import ptit.bookstore.model.Publisher;

@Repository
public class PublisherDao {
	@Autowired
	private DataSource dataSource;
  
  @Autowired
  private JdbcTemplate jdbcTemplate;

	public List<Publisher> findAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Publisher> listPublisher = new ArrayList<Publisher>();;
		try {
			conn = dataSource.getConnection();
			String sql = "select * from publisher";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {				
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt("id"));
				publisher.setName(rs.getString("name"));
				listPublisher.add(publisher);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPublisher;
  }
	
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
