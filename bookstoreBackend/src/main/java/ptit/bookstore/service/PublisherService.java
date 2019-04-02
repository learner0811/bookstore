package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.PublisherDao;
import ptit.bookstore.model.Publisher;

@Service
public class PublisherService {
	@Autowired
	public PublisherDao pulisherDao;
	
	public List<Publisher> findAll(){
		return pulisherDao.findAll();
	}
	
}
