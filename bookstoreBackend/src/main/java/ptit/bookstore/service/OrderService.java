package ptit.bookstore.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.OrderDao;
import ptit.bookstore.model.Book;
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
	
	public List<Order> getListOrder() {		
		List<Order> listOrder = orderDao.getListOrder();			
		return listOrder; 			
	}
	
	public List<Book> getListBookByOrder(int id){
		List<Book> listBook = orderDao.getListBook(id);
		return listBook;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean ChangeStatus(int orderId) {
		return orderDao.changeStatus(orderId);
	}
}
