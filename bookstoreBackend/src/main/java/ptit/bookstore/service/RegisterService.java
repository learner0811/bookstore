package ptit.bookstore.service;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.AccountDao;
import ptit.bookstore.dao.AddressDao;
import ptit.bookstore.dao.UserDao;
import ptit.bookstore.model.Account;
import ptit.bookstore.model.Address;
import ptit.bookstore.model.User;

@Service
public class RegisterService {
	@Autowired
	public AddressDao addressDao;
	
	@Autowired
	public AccountDao accountDao;
	
	@Autowired
	public UserDao userDao;	
	
	@Autowired
	public PlatformTransactionManager transactionManager;
	
	@Transactional(rollbackFor = Exception.class)
	public User register(User user) throws SQLException{
		//check account
		if(accountDao.isAccountExists(user.getAccount()))
			return null;
		
		//save account				
		Account account = accountDao.Save(user.getAccount());				
		user.setAccount(account);
		
		//save address		
		Address address = addressDao.Save(user.getAddress());
		user.setAddress(address);
		
		//save user		
		user = userDao.Save(user);		
		return user;
	}
}
