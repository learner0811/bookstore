package ptit.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.model.Order;
import ptit.bookstore.model.Publisher;
import ptit.bookstore.model.User;
import ptit.bookstore.service.OrderService;

@RestController
public class OrderApiCtr {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public ResponseEntity<Boolean> register(@RequestBody Order order){		
		Boolean result = orderService.addOrder(order);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getOrderByUserId", method = RequestMethod.GET)
	public ResponseEntity<List<Order>> getOrderByUserId(@RequestParam int userId){		
		List<Order> result = orderService.getOrderByUserId(userId);
		return new ResponseEntity<List<Order>>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
