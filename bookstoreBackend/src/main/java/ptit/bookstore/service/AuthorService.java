package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.AuthorDao;
import ptit.bookstore.model.Author;

@Service
public class AuthorService {
	@Autowired
	private AuthorDao authorDao;
	
	public List<Author> findAll(){
		return authorDao.findAll();
	}
}
