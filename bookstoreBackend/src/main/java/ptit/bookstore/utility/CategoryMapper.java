package ptit.bookstore.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import ptit.bookstore.model.Category;

public class CategoryMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet arg0, int arg1) throws SQLException {
		Category c = new Category();
		c.setId(arg0.getInt("category.id"));
		c.setName(arg0.getString("category.name"));
		return c;
	}


}
