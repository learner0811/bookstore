package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Staff;
import ptit.bookstore.utility.StaffRowMapper;

@Repository
public class StaffDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Staff> findAll() {
		List<Staff> listStaff = null;
		String sql = "SELECT a.* , b.*" + " FROM bookstore.user a" + " inner join account b" + " on a.idAccount = b.id "
				+ "where a.role = 'Staff';";
		listStaff = jdbcTemplate.query(sql, new StaffRowMapper());
		return listStaff;
	}

	public boolean save(final Staff staff) {
		int affectRow = jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "insert into user (idAccount, email, role, name) values(?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, staff.getAccount().getId());
				ps.setString(2, staff.getEmail());
				ps.setString(3, staff.getRole());
				ps.setString(4, staff.getName());
				return ps;
			}
		});
		System.out.println("row affect in save staff dao save functoin " + affectRow);
		return true;
	}
}
