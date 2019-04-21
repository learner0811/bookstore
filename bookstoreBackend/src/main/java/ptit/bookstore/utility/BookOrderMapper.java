package ptit.bookstore.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ptit.bookstore.model.Book;
import ptit.bookstore.model.BookInfo;

public class BookOrderMapper implements RowMapper<Book>{

	public Book mapRow(ResultSet rs, int arg1) throws SQLException {
		Book book = new Book();
		book.setId(rs.getInt("a.id"));
		book.setStatus(rs.getString("a.status"));
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setId(rs.getInt("b.id"));
		bookInfo.setPrice(rs.getInt("b.price"));
		bookInfo.setName(rs.getString("b.name"));
		bookInfo.setDiscount(rs.getInt("b.discount"));
		bookInfo.setDescription(rs.getString("b.description"));
		book.setBookInfo(bookInfo);
		return book;
	}

}
