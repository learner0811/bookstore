package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Book getBookById(int id)
	{
		Book result = bookDao.getBookById(id);
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Book save(Book book) {
		book = bookDao.save(book);
		bookDao.addBookCategory(book);
		return book;
	}
}
