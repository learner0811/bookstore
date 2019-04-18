package ptit.bookstore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ptit.bookstore.model.User;
import ptit.bookstore.service.RegisterService;


@RestController
public class RegisterApiCtr {
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody User user){		
		try {
			user = registerService.register(user);	
		} catch (Exception ex) {
			ex.printStackTrace();
			user = new User();			
			return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<User>(user, user == null ? HttpStatus.CONFLICT : HttpStatus.OK);
	}
	
}
