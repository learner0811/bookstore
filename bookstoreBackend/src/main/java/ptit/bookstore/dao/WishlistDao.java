package ptit.bookstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Integer> getWishlistByUserId(int userId)
	{
		String sql = "select * from wishlist where clientId = ?";
		List<Integer> listResult = jdbcTemplate.query(sql, new Object[] {userId}, new WishlistMapper());
		return listResult;
	}
	
	public int addToWishlist(int userId, int bookId)
	{
		String sql = "insert into wishlist(clientId, bookId) values (?, ?)";
		int result = jdbcTemplate.update(sql, new Object[] {userId, bookId});
		return result;
	}
	
	private class WishlistMapper implements RowMapper<Integer>
	{

		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getInt("bookId");
		}
	}
}
