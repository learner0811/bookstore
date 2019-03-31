package ptit.bookstore.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ptit.bookstore.model.Account;
import ptit.bookstore.model.User;
import ptit.bookstore.service.LoginService;

@RestController
public class LoginApiCtr {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> doLogin(@RequestBody Account account) {			
		User user = loginService.doLogin(account);		
		if (user.getId() != 0)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		
	}
}
