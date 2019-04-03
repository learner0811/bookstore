package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.BookInfoDao;
import ptit.bookstore.model.BookInfo;

@Service
public class BookService {
	@Autowired
	private BookInfoDao bookDao;
	
	public List<BookInfo> searchBookByName(String bookName)
	{
		List<BookInfo> result = bookDao.getBookByName(bookName);
		return result;
		
	}
	
	public List<BookInfo> getAllBook()
	{
		List<BookInfo> result = bookDao.getAllBook();
		return result;
	}
	
	public BookInfo getBookById(int id)
	{
		BookInfo result = bookDao.getBookById(id);
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BookInfo save(BookInfo book) {
		book = bookDao.save(book);
		bookDao.addBookCategory(book);
		return book;
  }
  
	public List<BookInfo> searchBookByCategory(int categoryId)
	{
		List<BookInfo> result = bookDao.getBookByCategory(categoryId);
		return result;
	}
	
	public double getUserRating(int bookId, int userId)
	{
		double result = bookDao.getUserRating(bookId, userId);
		return result;
	}
}
