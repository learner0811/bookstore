package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.Book;
import ptit.bookstore.model.BookInfo;
import ptit.bookstore.model.Order;
import ptit.bookstore.utility.BookOrderMapper;
import ptit.bookstore.utility.OrderRowMapper;

@Repository
public class OrderDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ShippingInfoDao shippingInfoDao;
	
	@Autowired
	private BookDao bookDao;
	
	public boolean addOrder(final Order order)
	{
		//check if there are enough books for that order
		for(BookInfo bookinfo : order.getCart().getListBook())
		{
			//request more book that currently have
			if(bookinfo.getAvailableQuantity() > bookDao.getAvailableNumber(bookinfo.getId()))
				return false;
		}
		//create shipping info
		final int shippinginfoId = shippingInfoDao.addShippingInfo(order.getShippingInfo());
		//create order
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				String sql = "insert into bookstore.order (dateCreate, clientId, shippinginfoId, status, paymentmethod) "
						+ "values (?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setDate(1, order.getDateCreate());
				ps.setInt(2, order.getClient().getId());
				ps.setInt(3, shippinginfoId);
				ps.setString(4, "In Progress");
				ps.setString(5, order.getPaymentMethod());
				return ps;
			}
		}, holder);
		//add those books to order
		for(BookInfo bookinfo : order.getCart().getListBook())
		{
			for(int i = 0; i < bookinfo.getAvailableQuantity(); i++)
			{
				bookDao.addToOrder(bookinfo.getId(), holder.getKey().intValue());
			}
		}
		return true;
	}

	public List<Order> getListOrder() {
		List<Order> listOrder = null;
		String sql = "select a.*, b.*, c.*"
				+ " from bookstore.order a"
				+ " left join user b"
				+ " on a.clientid = b.idclient"
				+ " left join shippinginfo c"
				+ " on a.shippinginfoid = c.id"
				+ " where role = 'Customer'";		
		listOrder= jdbcTemplate.query(sql, new OrderRowMapper());		
		return listOrder;
	}

	public List<Book> getListBook(final int id) {
		List<Book> listBook = null;		
		listBook = jdbcTemplate.query(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "SELECT * FROM book a inner join bookinfo b on a.bookinfoid = b.id where a.orderid = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				return ps;
			}
		}, new BookOrderMapper());		
		return listBook;
	}

	public boolean changeStatus(int orderId) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "update bookstore.order"
						+ " set status = case "
						+ "when status = 'In Progress' then 'Done' "
						+ "when status = 'Done' then 'In Progress' end where id = 6;";						
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		});		
		return true;
		
	}
}
