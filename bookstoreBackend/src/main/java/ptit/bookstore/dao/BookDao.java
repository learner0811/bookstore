package ptit.bookstore.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import ptit.bookstore.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class BookDao {
	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Book getBookById(int id)
	{
		String sql = "select * from book "
				+ "left join author on book.authorId = author.id "
				+ "left join publisher on book.publisherId = publisher.id "
				+ "left join book_category on book.id = book_category.idbook "
				+ "left join category on book_category.idcat = category.id "
				+ "where book.id = ?";
		PreparedStatement ps;
		try {
			ps = dataSource.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Book b = new Book();
			while(rs.next())
			{
				if(b.getId() != id)
				{
					b.setId(id);
					b.setName(rs.getString("book.name"));
					Author author = new Author();
					author.setId(rs.getInt("book.authorId"));
					author.setDob(rs.getDate("author.dob"));
					author.setName(rs.getString("author.name"));
					b.setAuthor(author);
					Publisher publisher = new Publisher();
					publisher.setId(rs.getInt("book.publisherId"));
					publisher.setName(rs.getString("publisher.name"));
					b.setPublisher(publisher);
					b.setPrice(rs.getInt("book.price"));
					b.setDiscount(rs.getInt("book.discount"));
					b.setStatus(rs.getString("book.status"));
					b.setImgUrl(rs.getString("book.imgUrl"));
					b.setDescription(rs.getString("book.description"));
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
				else
				{
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
			}
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Book> getBookByName(String bookName)
	{
		bookName = bookName.toLowerCase();
		bookName = "%" + bookName + "%";
		List<Book> result = new ArrayList<Book>();
		try {
			String sql = "select * from book "
					+ "left join author on book.authorId = author.id "
					+ "left join publisher on book.publisherId = publisher.id "
					+ "left join book_category on book.id = book_category.idbook "
					+ "left join category on book_category.idcat = category.id "
					+ "where lower(book.name) like ?";
			PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
			ps.setString(1, bookName);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("book.id");
				Book b = isBookInList(id, result);
				if(b == null)
				{
					b = new Book();
					b.setId(id);
					b.setName(rs.getString("book.name"));
					Author author = new Author();
					author.setId(rs.getInt("book.authorId"));
					author.setDob(rs.getDate("author.dob"));
					author.setName(rs.getString("author.name"));
					b.setAuthor(author);
					Publisher publisher = new Publisher();
					publisher.setId(rs.getInt("book.publisherId"));
					publisher.setName(rs.getString("publisher.name"));
					b.setPublisher(publisher);
					b.setPrice(rs.getInt("book.price"));
					b.setDiscount(rs.getInt("book.discount"));
					b.setStatus(rs.getString("book.status"));
					b.setImgUrl(rs.getString("book.imgUrl"));
					b.setDescription(rs.getString("book.description"));
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
					result.add(b);
				}
				else
				{
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
		
	}
	
	public List<Book> getBookByCategory(int categoryId)
	{
		List<Book> result = new ArrayList<Book>();
		try {
			String sql = "select * from book "
					+ "left join book_category on book.id = book_category.idbook "
					+ "left join category on book_category.idcat = category.id "
					+ "where category.id = ?";
			PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("book.id");
				Book b = this.getBookById(id);
				result.add(b);
			}
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private Book isBookInList(int id, List<Book> listBook)
	{
		for(Book b : listBook)
			if(b.getId() == id)
				return b;
		return null;
	}
	
//	private class BookRowMapper implements RowMapper<Book>
//	{
//
//		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
//			Book b = new Book();
//			b.setId(rs.getInt("book.id"));
//			Author author = new Author();
//			author.setId(rs.getInt("book.authorId"));
//			author.setDob(rs.getDate("author.dob"));
//			author.setName(rs.getString("author.name"));
//			b.setAuthor(author);
//			Publisher publisher = new Publisher();
//			publisher.setId(rs.getInt("book.publisherId"));
//			publisher.setName(rs.getString("publisher.name"));
//			b.setPrice(rs.getInt("book.price"));
//			b.setDiscount(rs.getInt("book.discount"));
//			b.setStatus(rs.getString("book.status"));
//			b.setImgUrl(rs.getString("book.imgUrl"));
//			b.setDescription(rs.getString("book.description"));
//			return b;
//		}
//	}
}
