package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.BookDao;
import ptit.bookstore.dao.BookInfoDao;
import ptit.bookstore.model.BookInfo;

@Service
public class BookService {
	@Autowired
	private BookInfoDao bookInfoDao;

	@Autowired
	private BookDao bookDao;

	public List<BookInfo> searchBookByName(String bookName) {
		List<BookInfo> result = bookInfoDao.getBookByName(bookName);
		return result;

	}

	public List<BookInfo> getAllBook() {
		List<BookInfo> result = bookInfoDao.getAllBook();
		return result;
	}

	public BookInfo getBookById(int id) {
		BookInfo result = bookInfoDao.getBookById(id);
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public BookInfo save(BookInfo book) {
		book = bookInfoDao.save(book);
		System.out.println("book name is " + book.getName());
		bookInfoDao.addBookCategory(book);
		return book;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean update(BookInfo book) {
		boolean flag = true;
		flag = bookInfoDao.update(book);
		flag = bookInfoDao.deleteBookCategory(book);
		bookInfoDao.addBookCategory(book);
		return flag;
	}

	public List<BookInfo> searchBookByCategory(int categoryId) {
		List<BookInfo> result = bookInfoDao.getBookByCategory(categoryId);
		return result;
	}

	public double getUserRating(int bookId, int userId) {
		double result = bookInfoDao.getUserRating(bookId, userId);
		return result;
	}

	public void delete(int id) {
		bookInfoDao.delete(id);
	}

	public String updateQuantity(BookInfo book, int newQuantity) {
		int availableQuanity = book.getAvailableQuantity();
		if (availableQuanity < newQuantity) {
			int number = newQuantity - availableQuanity;
			for (int i = 0; i < number; i++)
				bookDao.addBook(book.getId());
			return "Add more book successful";
		} else if (availableQuanity > newQuantity) {
			int number = availableQuanity - newQuantity;
			for (int i = 0; i < number; i++) {
				bookDao.deleteBook(book.getId());				
			}
			return "Delete boo successful";
		}		
		return "Available quanity equal to new quantity";

	}

	public List<BookInfo> searchBookByPublisher(int publisherId) {
		List<BookInfo> result = bookInfoDao.getBookByPublisher(publisherId);
		return result;
	}

	public List<BookInfo> getRecentBook(int number) {
		List<BookInfo> result = bookInfoDao.getRecentBook(number);
		return result;
	}

	public List<BookInfo> getDiscountBook(int number) {
		List<BookInfo> result = bookInfoDao.getDiscountBook(number);
		return result;
	}

}
