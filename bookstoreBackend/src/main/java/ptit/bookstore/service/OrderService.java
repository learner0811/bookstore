package ptit.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.OrderDao;
import ptit.bookstore.model.Order;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Transactional(rollbackFor = Exception.class)
	public boolean addOrder(Order order)
	{
		boolean result = orderDao.addOrder(order);
		return result;
	}
}
