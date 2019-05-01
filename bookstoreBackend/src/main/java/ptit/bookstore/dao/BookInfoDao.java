package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;
import ptit.bookstore.model.*;
import ptit.bookstore.utility.BookInfoMapper;
import ptit.bookstore.utility.CategoryMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BookInfoDao {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BookDao bookDao;

	private Connection conn;

	public List<BookInfo> getAllBook() {
		List<BookInfo> result = new ArrayList<BookInfo>();
		try {
			String sql = "select * from bookinfo " + "left join author on bookinfo.authorId = author.id "
					+ "left join publisher on bookinfo.publisherId = publisher.id "
					+ "left join book_category on bookinfo.id = book_category.idbook "
					+ "left join category on book_category.idcat = category.id ";
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("bookinfo.id");
				BookInfo b = isBookInList(id, result);
				if (b == null) {
					sql = "select * from bookinfo join rating on bookinfo.id = rating.bookId "
							+ "where bookinfo.id = ?";
					b = new BookInfo();
					b.setId(id);
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
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
					ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					double rating = 0;
					ResultSet temp = ps.executeQuery();
					int count = 0;
					while (temp.next()) {
						rating += temp.getDouble("rating.numberOfStar");
						count++;
					}
					if (count == 0)
						rating = 0;
					else
						rating /= count;
					b.setAverageRating(rating);
					int quantity = bookDao.getAvailableNumber(id);
					b.setAvailableQuantity(quantity);
					result.add(b);
					temp.close();
				} else {
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
			}
			ps.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public double getRating(final int id)
	{
		List<Double> result = jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				String sql = "select * from bookinfo join rating on bookinfo.id = rating.bookId " + "where bookinfo.id = ?";
				PreparedStatement ps = arg0.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
		}, new RatingMapper());
		if(result.size() == 0)
			return 0;
		double sum = 0;
		for(Double d : result)
			sum += d;
		sum /= result.size();
		return sum;
	}
	
	private class RatingMapper implements RowMapper<Double>
	{
		@Override
		public Double mapRow(ResultSet arg0, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Double d = arg0.getDouble("numberOfStar");
			return d;
		}
	}

	/*public BookInfo getBookById(int id) {
		BookInfo b = null;
		String sql = "select * from bookinfo " + "left join author on bookinfo.authorId = author.id "
				+ "left join publisher on bookinfo.publisherId = publisher.id "
				+ "left join book_category on bookinfo.id = book_category.idbook "
				+ "left join category on book_category.idcat = category.id " + "where bookinfo.id = ?";
		PreparedStatement ps;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			b = new BookInfo();
			while (rs.next()) {
				if (b.getId() != id) {
					b.setId(id);
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
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
					ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					
					//set rating
					double rating = this.getRating(id);
					b.setAverageRating(rating);
					
					int quantity = bookDao.getAvailableNumber(id);
					b.setAvailableQuantity(quantity);
				} else {
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
			}
			conn.close();
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}*/
	
	public BookInfo getBookById(final int id)
	{
		
		List<BookInfo> listBook = jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				String sql = "select * from bookinfo " + "left join author on bookinfo.authorId = author.id "
						+ "left join publisher on bookinfo.publisherId = publisher.id " + "where bookinfo.id = ?";
				PreparedStatement ps = arg0.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
		}, new BookInfoMapper());
		BookInfo b = listBook.get(0);
		if(b == null)
			return null;
		b.setId(id);
		List<Category> listCategory = getBookCategory(id);
		for(Category c : listCategory)
			b.addCategory(c);
		double rating = this.getRating(id);
		b.setAverageRating(rating);
		int quantity = bookDao.getAvailableNumber(id);
		b.setAvailableQuantity(quantity);
		return b;
	}

	private List<Category> getBookCategory(final int id) {
		List<Category> result = jdbcTemplate.query(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				String sql = "select * from bookinfo "
						+ "left join book_category on bookinfo.id = book_category.idbook "
						+ "left join category on book_category.idcat = category.id " + "where bookinfo.id = ?";
				PreparedStatement ps = arg0.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
		}, new CategoryMapper());
		return result;
	}

	public List<BookInfo> getBookByName(String bookName) {
		bookName = bookName.toLowerCase();
		bookName = "%" + bookName + "%";
		List<BookInfo> result = new ArrayList<BookInfo>();
		try {
			String sql = "select * from bookinfo " + "left join author on bookinfo.authorId = author.id "
					+ "left join publisher on bookinfo.publisherId = publisher.id "
					+ "left join book_category on bookinfo.id = book_category.idbook "
					+ "left join category on book_category.idcat = category.id " + "where lower(bookinfo.name) like ?";
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bookName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("bookinfo.id");
				BookInfo b = isBookInList(id, result);
				if (b == null) {
					sql = "select * from bookinfo join rating on bookinfo.id = rating.bookId "
							+ "where bookinfo.id = ?";
					b = new BookInfo();
					b.setId(id);
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
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
					ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					double rating = 0;
					ResultSet temp = ps.executeQuery();
					int count = 0;
					while (temp.next()) {
						rating += temp.getDouble("rating.numberOfStar");
						count++;
					}
					if (count == 0)
						rating = 0;
					else
						rating /= count;
					b.setAverageRating(rating);
					int quantity = bookDao.getAvailableNumber(id);
					b.setAvailableQuantity(quantity);
					result.add(b);
				} else {
					Category category = new Category();
					category.setId(rs.getInt("category.id"));
					category.setName(rs.getString("category.name"));
					b.addCategory(category);
				}
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public List<BookInfo> getBookByCategory(int categoryId) {
		List<BookInfo> result = new ArrayList<BookInfo>();
		try {
			String sql = "select * from bookinfo " + "left join book_category on bookinfo.id = book_category.idbook "
					+ "left join category on book_category.idcat = category.id " + "where category.id = ?";
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("bookinfo.id");
				BookInfo b = this.getBookById(id);
				result.add(b);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public double getUserRating(int bookId, int userId) {
		double result = 0;
		String sql = "select * from bookinfo " + "join rating on bookinfo.id = rating.bookId "
				+ "where bookinfo.id = ? and rating.userId = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookId);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getDouble("rating.numberOfStar");
			} else
				result = 0;
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	private BookInfo isBookInList(int id, List<BookInfo> listBook) {
		for (BookInfo b : listBook)
			if (b.getId() == id)
				return b;
		return null;
	}

	// private class BookRowMapper implements RowMapper<Book>
	// {
	//
	// public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
	// Book b = new Book();
	// b.setId(rs.getInt("book.id"));
	// Author author = new Author();
	// author.setId(rs.getInt("book.authorId"));
	// author.setDob(rs.getDate("author.dob"));
	// author.setName(rs.getString("author.name"));
	// b.setAuthor(author);
	// Publisher publisher = new Publisher();
	// publisher.setId(rs.getInt("book.publisherId"));
	// publisher.setName(rs.getString("publisher.name"));
	// b.setPrice(rs.getInt("book.price"));
	// b.setDiscount(rs.getInt("book.discount"));
	// b.setStatus(rs.getString("book.status"));
	// b.setImgUrl(rs.getString("book.imgUrl"));
	// b.setDescription(rs.getString("book.description"));
	// return b;
	// }
	// }

	public BookInfo save(final BookInfo book) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "insert into bookInfo (authorId, publisherId, price, imgUrl, name, discount) values (?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				if (book.getAuthor().getId() == 0)
					ps.setNull(1, Types.INTEGER);
				else
					ps.setInt(1, book.getAuthor().getId());
				if (book.getPublisher().getId() == 0)
					ps.setNull(2, Types.INTEGER);
				else
					ps.setInt(2, book.getPublisher().getId());
				ps.setInt(3, book.getPrice());
				ps.setString(4, book.getImgUrl());
				ps.setString(5, book.getName());
				ps.setInt(6, book.getDiscount());
				return ps;
			}
		}, holder);
		book.setId(holder.getKey().intValue());
		return book;
	}

	public void addBookCategory(final BookInfo book) {
		final List<Category> listCat = book.getCategory();
		int rowAffect = 0;
		for (int i = 0; i < listCat.size(); i++) {
			rowAffect += jdbcTemplate.update("insert into book_category values (?, ?)", book.getId(),
					listCat.get(i).getId());
		}

		System.out.println("number of row affect in book dao function addBookCategory " + rowAffect);
	}

	public void delete(final int id) {
		int rowAffect = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "delete from bookinfo where id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
		});
		System.out.println("id is " + id);
		System.out.println("Number of row is deleted " + rowAffect);
	}

	public boolean update(final BookInfo book) {
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					String sql = "update bookinfo set authorId = ?, publisherId = ?, name = ?, price = ?, discount = ?, imgUrl = ?, description = ?"
							+ " where id = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					if (book.getAuthor().getId() == 0)
						ps.setNull(1, Types.INTEGER);
					else
						ps.setInt(1, book.getPublisher().getId());
					if (book.getPublisher().getId() == 0)
						ps.setNull(2, Types.INTEGER);
					else
						ps.setInt(2, book.getPublisher().getId());
					ps.setString(3, book.getName());
					ps.setInt(4, book.getPrice());
					ps.setInt(5, book.getDiscount());
					ps.setString(6, book.getImgUrl());
					ps.setString(7, book.getDescription());
					ps.setInt(8, book.getId());
					return ps;
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteBookCategory(BookInfo book) {
		try {			
			int rowAffect = 0;			
			rowAffect += jdbcTemplate.update("delete from book_category where idbook = ?", book.getId());			
			System.out.println("number of row affect " + rowAffect);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	public List<BookInfo> getBookByPublisher(int publisherId) {
		List<BookInfo> result = new ArrayList<BookInfo>();
		try {
			String sql = "select * from bookinfo where publisherId = ?";
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, publisherId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("bookinfo.id");
				BookInfo b = this.getBookById(id);
				result.add(b);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<BookInfo> getRecentBook(int number) {
		List<BookInfo> listAll = this.getAllBook();
		int size = listAll.size();
		if(number > size)
			number = size;
		Collections.sort(listAll, new BookCompare());
		for(int i = 0; i < size - number; i++)
			listAll.remove(0);
		return listAll;
	}
	
	public List<BookInfo> getDiscountBook(int number) {
		List<BookInfo> listAll = this.getAllBook();
		int size = listAll.size();
		if(number > size)
			number = size;
		Collections.sort(listAll, new DiscountCompare());
		for(int i = 0; i < size - number; i++)
			listAll.remove(0);
		List<BookInfo> temp = new ArrayList<>();
		for(BookInfo book : listAll)
		{
			if(book.getDiscount() != 0)
				temp.add(book);
		}
		return temp;
	}
	
	public List<BookInfo> getBookByOrder(int orderId)
	{
		List<BookInfo> result = new ArrayList<>();
		String sql = "SELECT * FROM book a inner join bookinfo b on a.bookinfoid = b.id where a.orderid = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int infoId = rs.getInt("b.id");
				BookInfo info = isBookInList(infoId, result);
				if(info == null)
				{
					info = this.getBookById(infoId);
					System.out.println("book retrieved by id");
					info.setQuantity(1);
					result.add(info);
				}
				else
				{
					info.setQuantity(info.getQuantity()+1);
					continue;
				}
			}
			ps.close();
			rs.close();
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public void addExistingBook(int bookinfoId, int quantity) { for(int i = 0; i
	 * < quantity; i++) { jdbcTemplate.update(new PreparedStatementCreator() {
	 * 
	 * @Override public PreparedStatement createPreparedStatement(Connection con)
	 * throws SQLException { String sql = "insert into " +
	 * "book(book.status, book.bookinfoId) " + "values (?, ?)"; PreparedStatement ps
	 * = con.prepareStatement(sql); ps.setInt(1, 1); ps.setInt(2, bookinfoId);
	 * return ps; } }); } }
	 */
	
	//comparing books by id
	class BookCompare implements Comparator<BookInfo>
	{
		@Override
		public int compare(BookInfo arg0, BookInfo arg1) {
			return arg0.getId() - arg1.getId();
		}
	}
	
	//comparing books by discount
	class DiscountCompare implements Comparator<BookInfo>
	{
		@Override
		public int compare(BookInfo arg0, BookInfo arg1) {
			return arg0.getDiscount() - arg1.getDiscount();
		}
	}
}
