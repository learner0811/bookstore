package ptit.bookstore.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.model.Address;
import ptit.bookstore.model.Author;
import ptit.bookstore.model.BookInfo;
import ptit.bookstore.model.Category;
import ptit.bookstore.service.BookService;



@RestController
public class BookApiCtr {
	@Autowired
	private BookService bookService;
	
	/*Get All books*/	
	@RequestMapping(value = "/getAllBook", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getAllBook(){
		List<BookInfo> result = bookService.getAllBook();
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/searchBookByName", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getBookByName(@RequestParam String bookName)
	{
		List<BookInfo> result = bookService.searchBookByName(bookName);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getBookById", method = RequestMethod.GET)
	public ResponseEntity<BookInfo> getBookById(@RequestParam int id)
	{
		BookInfo result = bookService.getBookById(id);
		return new ResponseEntity<BookInfo>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchBookByCategory", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getBookByCategory(@RequestParam int categoryId)
	{
		List<BookInfo> result = bookService.searchBookByCategory(categoryId);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getUserRating", method = RequestMethod.GET)
	public ResponseEntity<Double> getUserRating(@RequestParam int bookId, @RequestParam int userId)
	{
		Double result = bookService.getUserRating(bookId, userId);
		return new ResponseEntity<Double>(result, result == -1 ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/searchBookByPublisher", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> searchBookByPublisher(@RequestParam int publisherId)
	{
		List<BookInfo> result = bookService.searchBookByPublisher(publisherId);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getRecentBook", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getRecentBook(@RequestParam int number)
	{
		List<BookInfo> result = bookService.getRecentBook(number);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getDiscountBook", method = RequestMethod.GET)
	public ResponseEntity<List<BookInfo>> getDiscountBook(@RequestParam int number)
	{
		List<BookInfo> result = bookService.getDiscountBook(number);
		return new ResponseEntity<List<BookInfo>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
