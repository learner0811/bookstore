package ptit.bookstore.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.model.Address;
import ptit.bookstore.model.Author;
import ptit.bookstore.model.Book;
import ptit.bookstore.model.Category;



@RestController
public class BookApiCtr {
	
	
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
	
}
