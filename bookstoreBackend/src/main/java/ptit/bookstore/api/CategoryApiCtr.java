package ptit.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.dao.CategoryDao;
import ptit.bookstore.model.Category;

@RestController
public class CategoryApiCtr {
	@Autowired
	private CategoryDao categoryDao;
	
	@RequestMapping("/getAllCategory")
	public ResponseEntity<List<Category>> findAll(){
		List<Category> listCategory = categoryDao.findAll();
		return new ResponseEntity<List<Category>>(listCategory, HttpStatus.OK);
	}
	
}
