package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import ptit.bookstore.model.Cart;
import ptit.bookstore.model.Client;
import ptit.bookstore.model.Order;
import ptit.bookstore.model.ShippingInfo;
import ptit.bookstore.model.User;
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
	
	@Autowired
	private BookInfoDao bookinfoDao;
	
	@Autowired
	private UserDao userDao;
	
	private Connection conn;
	
	public boolean addOrder(final Order order)
	{
		//check if there are enough books for that order
		for(BookInfo bookinfo : order.getCart().getListBook())
		{
			//request more book that currently have
			if(bookinfo.getQuantity() > bookDao.getAvailableNumber(bookinfo.getId()))
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
			for(int i = 0; i < bookinfo.getQuantity(); i++)
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

	public boolean changeStatus(final int orderId) {
		System.out.println("orderid = " + orderId);
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "update bookstore.order"
						+ " set status = case "
						+ "when status = 'In Progress' then 'Done' "
						+ "when status = 'Done' then 'In Progress' end where id = ?;";						
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, orderId);
				return ps;
			}
		});		
		return true;
		
	}

	public List<Order> getOrderByUserId(int userId) {
		List<Order> result = new ArrayList<Order>();
		String sql = "select * from bookstore.`order` "
				+ "left join shippinginfo "
				+ "on `order`.shippinginfoId = shippinginfo.id "
				+ "where bookstore.`order`.clientId = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Order o = new Order();
				o.setDateCreate(rs.getDate("dateCreate"));
				o.setId(rs.getInt("order.id"));
				o.setPaymentMethod(rs.getString("paymentmethod"));
				o.setStatus(rs.getString("status"));
				
				ShippingInfo info = new ShippingInfo();
				info.setId(rs.getInt("shippinginfo.id"));
				info.setCity(rs.getString("shippinginfo.city"));
				info.setDistrict("shippinginfo.district");
				info.setNumber(rs.getString("shippinginfo.number"));
				info.setReceiverName(rs.getString("shippinginfo.number"));
				info.setZipcode(rs.getString("shippinginfo.zipcode"));
				o.setShippingInfo(info);
				
				int clientId = rs.getInt("order.clientId");
				User user = userDao.getUserById(clientId);
				Client client = new Client();
				client.setAccount(user.getAccount());
				client.setAddress(user.getAddress());
				client.setEmail(user.getEmail());
				client.setId(user.getId());
				client.setName(user.getName());
				client.setRole(user.getRole());
				client.setWishList(null);
				o.setClient(client);
				
				Cart cart = new Cart();
				List<BookInfo> listBook = bookinfoDao.getBookByOrder(o.getId());
				cart.setListBook(listBook);
				o.setCart(cart);
				
				result.add(o);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
