package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
		String sql = "select * from book where bookinfoId = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookinfoId);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next())
			{
				if(rs.getString("status").equals("1"))
					count++;
			}
			conn.close();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
