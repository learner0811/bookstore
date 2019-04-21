package ptit.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.bookstore.dao.AccountDao;
import ptit.bookstore.dao.StaffDao;
import ptit.bookstore.dao.UserDao;
import ptit.bookstore.model.Account;
import ptit.bookstore.model.Staff;

@Service
public class StaffService {
	@Autowired
	private StaffDao staffDao; 
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserDao userDao;
	
	public List<Staff> findAll(){
		return staffDao.findAll();
	}
	
	public boolean save(Staff staff) {
		boolean flag = true;
		try {
			Account account = accountDao.Save(staff.getAccount());
			staff.setAccount(account);
			flag = staffDao.save(staff);
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	
	public boolean delete(int id) {
		boolean flag = true;
		flag = accountDao.deleteAccountById(id);
		return flag;
	}
}
