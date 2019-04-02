package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.BookDao;
import ptit.bookstore.model.Book;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;
	
	public List<Book> searchBookByName(String bookName)
	{
		List<Book> result = bookDao.getBookByName(bookName);
		return result;
		
	}
	
	public List<Book> getAllBook()
	{
		List<Book> result = bookDao.getAllBook();
		return result;
	}
	
	public Book getBookById(int id)
	{
		Book result = bookDao.getBookById(id);
		return result;
	}
	
	public List<Book> searchBookByCategory(int categoryId)
	{
		List<Book> result = bookDao.getBookByCategory(categoryId);
		return result;
	}
	
	public double getUserRating(int bookId, int userId)
	{
		double result = bookDao.getUserRating(bookId, userId);
		return result;
	}
}
