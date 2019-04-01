package ptit.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.service.PublisherService;
import ptit.bookstore.model.Publisher;

@RestController
public class PublisherApiCtr {
	@Autowired
	private PublisherService publisherService;
	
	@RequestMapping(value = "/getAllPublisher", method = RequestMethod.GET)
	public ResponseEntity<List<Publisher>> getAllPublisher()
	{
		List<Publisher> result = publisherService.getAllPublisher();
		return new ResponseEntity<List<Publisher>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPublisherById", method = RequestMethod.GET)
	public ResponseEntity<Publisher> getPublisherById(@RequestParam int id)
	{
		Publisher result = publisherService.getPublisherById(id);
		return new ResponseEntity<Publisher>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
