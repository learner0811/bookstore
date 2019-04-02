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

import ptit.bookstore.model.Publisher;

@Repository
public class PublisherDao {
	@Autowired
	private DataSource dataSource;

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
}
