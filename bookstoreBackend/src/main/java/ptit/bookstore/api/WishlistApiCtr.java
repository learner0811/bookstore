package ptit.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.model.BookInfo;
import ptit.bookstore.service.WishlistService;

@RestController
public class WishlistApiCtr {
	@Autowired
	private WishlistService wishlistService;
	
	@RequestMapping(value = "/getWishlist", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getWishlist(@RequestParam int userId)
	{
		List<BookInfo> result = wishlistService.getWishlist(userId);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addToWishlist", method = RequestMethod.GET)
	public ResponseEntity<Boolean> addToWishlist(@RequestParam int userId, @RequestParam int bookId)
	{
		Boolean result = wishlistService.addToWishlist(userId, bookId);
		return new ResponseEntity<Boolean>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeFromWishlist", method = RequestMethod.GET)
	public ResponseEntity<Boolean> removeFromWishlist(@RequestParam int userId, @RequestParam int bookId)
	{
		Boolean result = wishlistService.removeFromWishlist(userId, bookId);
		return new ResponseEntity<Boolean>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
