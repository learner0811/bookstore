package ptit.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.UserDao;
import ptit.bookstore.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User getUserById(int userId)
	{
		return userDao.getUserById(userId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean changeInfo(User user)
	{
		return userDao.changeInfo(user);
	}
}
