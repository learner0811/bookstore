package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.PublisherDao;
import ptit.bookstore.model.Publisher;

@Service
public class PublisherService {
	@Autowired
	private PublisherDao publisherDao;
	
	public List<Publisher> findAll(){
		return publisherDao.findAll();
	}
	
	public List<Publisher> getAllPublisher()
	{
		List<Publisher> result = publisherDao.getAllPublisher();
		return result;
	}
	
	public Publisher getPublisherById(int id)
	{
		Publisher result = publisherDao.getPublisherById(id);
		return result;
	}
}
