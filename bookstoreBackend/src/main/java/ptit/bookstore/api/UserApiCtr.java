package ptit.bookstore.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptit.bookstore.model.BookInfo;
import ptit.bookstore.model.User;
import ptit.bookstore.service.UserService;

@Controller
public class UserApiCtr {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getUserById", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@RequestParam int userId){
		User result = userService.getUserById(userId);
		return new ResponseEntity<User>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public ResponseEntity<Boolean> changeInfo(@RequestBody User user){
		Boolean result = userService.changeInfo(user);
		return new ResponseEntity<Boolean>(result, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
