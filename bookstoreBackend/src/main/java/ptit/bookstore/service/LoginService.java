package ptit.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.AccountDao;
import ptit.bookstore.dao.AddressDao;
import ptit.bookstore.dao.UserDao;
import ptit.bookstore.model.Account;
import ptit.bookstore.model.Address;
import ptit.bookstore.model.User;

@Service
public class LoginService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private AddressDao addressDao;
	
	public User doLogin(Account account) {
		account = accountDao.findOne(account);
		User user = userDao.findUserByAccount(account);	
		Address address = addressDao.findOne(user.getAddress().getId());
		user.setAddress(address);
		return user;
	}
}
