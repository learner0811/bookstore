package ptit.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.BookDao;
import ptit.bookstore.dao.WishlistDao;
import ptit.bookstore.model.Book;

@Service
public class WishlistService {
	@Autowired
	private WishlistDao wishlistDao;
	
	@Autowired
	private BookDao bookDao;
	
	public List<Book> getWishlist(int userId)
	{
		List<Integer> listBookId = wishlistDao.getWishlistByUserId(userId);
		List<Book> result = new ArrayList<>();
		for(int i : listBookId)
		{
			Book b = bookDao.getBookById(i);
			result.add(b);
		}
		return result;
	}
	
	public boolean addToWishlist(int userId, int bookId)
	{
		int result = wishlistDao.addToWishlist(userId, bookId);
		return result > 0 ? true : false;
	}
}
