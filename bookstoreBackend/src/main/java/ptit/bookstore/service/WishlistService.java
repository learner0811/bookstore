package ptit.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.BookInfoDao;
import ptit.bookstore.dao.WishlistDao;
import ptit.bookstore.model.BookInfo;

@Service
public class WishlistService {
	@Autowired
	private WishlistDao wishlistDao;
	
	@Autowired
	private BookInfoDao bookDao;
	
	public List<BookInfo> getWishlist(int userId)
	{
		List<Integer> listBookId = wishlistDao.getWishlistByUserId(userId);
		List<BookInfo> result = new ArrayList<BookInfo>();
		for(int i : listBookId)
		{
			BookInfo b = bookDao.getBookById(i);
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
