package ptit.bookstore.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import ptit.bookstore.model.Author;
import ptit.bookstore.model.BookInfo;
import ptit.bookstore.model.Publisher;

public class BookInfoMapper implements RowMapper<BookInfo> {

	@Override
	public BookInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		BookInfo b = new BookInfo();
		b.setName(rs.getString("bookinfo.name"));
		Author author = new Author();
		author.setId(rs.getInt("bookinfo.authorId"));
		author.setDob(rs.getDate("author.dob"));
		author.setName(rs.getString("author.name"));
		b.setAuthor(author);
		Publisher publisher = new Publisher();
		publisher.setId(rs.getInt("bookinfo.publisherId"));
		publisher.setName(rs.getString("publisher.name"));
		b.setPublisher(publisher);
		b.setPrice(rs.getInt("bookinfo.price"));
		b.setDiscount(rs.getInt("bookinfo.discount"));
		b.setImgUrl(rs.getString("bookinfo.imgUrl"));
		b.setDescription(rs.getString("bookinfo.description"));
		return b;
	}


}
