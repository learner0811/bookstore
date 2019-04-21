package ptit.bookstore.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ptit.bookstore.model.Account;
import ptit.bookstore.model.Staff;

public class StaffRowMapper implements RowMapper<Staff>{

	public Staff mapRow(ResultSet rs, int arg1) throws SQLException {
		Staff staff = new Staff();
		staff.setId(rs.getInt("a.idClient"));
		staff.setEmail(rs.getString("a.email"));
		staff.setRole(rs.getString("a.role"));
		staff.setName(rs.getString("a.name"));
		
		Account account = new Account();
		account.setId(rs.getInt("b.id"));
		account.setUsername(rs.getString("b.username"));
		account.setPassword(rs.getString("b.password"));
		staff.setAccount(account);
		return staff;
	}

}
