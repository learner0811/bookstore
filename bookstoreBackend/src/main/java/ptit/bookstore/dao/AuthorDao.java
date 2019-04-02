package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Author;


@Repository
public class AuthorDao {
	@Autowired
	private DataSource dataSource;

	public List<Author> findAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		List<Author> listAuthor = new ArrayList<Author>();		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from author";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setName(rs.getString("name"));
				listAuthor.add(author);
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
		return listAuthor;
	}
}
