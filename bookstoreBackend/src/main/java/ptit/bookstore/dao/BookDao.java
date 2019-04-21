package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	private Connection conn;
	
	/**
	 * This function return the number of available books with bookinfoId
	 * @param bookinfoId : id of bookinfo
	 * @return : number of available book
	 */
	public int getAvailableNumber(int bookinfoId)
	{
		String sql = "select count(*) from book where bookinfoId = ? and status = '1'";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookinfoId);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next())
				count = rs.getInt(1);
			conn.close();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This function returns an available book of bookinfo
	 * @param bookinfoId: id of bookinfo
	 * @return: id of one available book, -1 if none were found
	 */
	public int getAvailableBook(int bookinfoId)
	{
		String sql = "select * from book where bookinfoId = ? and status = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookinfoId);
			ps.setString(2, "1");
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if(rs.next())
			{
				conn.close();
				return rs.getInt("id");
			}
			conn.close();
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This function adds a new book with bookinfo specified in id
	 * @param bookinfoId: id of bookinfo
	 */
	public void addBook(final int bookinfoId)
	{
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into "
						+ "book(book.status, book.bookinfoId) "
						+ "values (?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, 1);
				ps.setInt(2, bookinfoId);
				return ps;
			}
		});
	}
	
	public void deleteBook(final int bookInfoId) {
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "delete from book where bookinfoid = ? and status = '1' limit 1";						
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, bookInfoId);				
				return ps;
			}
		});
	}
	
	/**
	 * This function adds a book to an order
	 * @param bookId: id of the book to be added
	 * @param orderId: id of the order that the book will be added in
	 */
	public void addToOrder(final int bookId, final int orderId)
	{
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "update book set status = 0, orderId = ? where bookinfoId = ? and status = 1 limit 1";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, orderId);
				ps.setInt(2, bookId);
				return ps;
			}
		});
	}
}
