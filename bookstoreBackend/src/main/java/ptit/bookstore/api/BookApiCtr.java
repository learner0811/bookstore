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
import ptit.bookstore.model.Book;
import ptit.bookstore.model.Category;
import ptit.bookstore.service.BookService;



@RestController
public class BookApiCtr {
	@Autowired
	private BookService bookService;
	
	/*Get All books*/	
	@RequestMapping("/getAllBook")
	public ResponseEntity<List<Book>> getAllBook(){
		List<Book> listBook = new ArrayList<Book>();
		Book b1 = new Book();
		b1.setId(1);
		b1.setPrice(12);
		b1.setStatus("sold");
		b1.setDescription("this book is ...");
		Author a = new Author();
		a.setId(1);
		a.setName("Chau viet cuong");
		a.setDob(new Date());
		Address address = new Address();
		address.setNumber("150 pho duc chinh");
		address.setDistrict("Ha Noi");
		address.setCountry("Viet Nam");
		a.setAddress(address);
		b1.setAuthor(a);
		Category cat = new Category();
		cat.setId(1);
		cat.setName("kinh di");
		listBook.add(b1);
		return new ResponseEntity<List<Book>>(listBook, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchBookByName", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getBookByName(@RequestParam String bookName)
	{
		List<Book> result = bookService.searchBookByName(bookName);
		return new ResponseEntity<List<Book>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getBookById", method = RequestMethod.GET)
	public ResponseEntity<Book> getBookById(@RequestParam int id)
	{
		Book result = bookService.getBookById(id);
		return new ResponseEntity<Book>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchBookByCategory", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getBookByCategory(@RequestParam int categoryId)
	{
		List<Book> result = bookService.searchBookByCategory(categoryId);
		return new ResponseEntity<List<Book>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
		
	}
}
