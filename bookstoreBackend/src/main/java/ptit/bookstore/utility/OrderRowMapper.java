package ptit.bookstore.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import ptit.bookstore.model.Client;
import ptit.bookstore.model.Order;
import ptit.bookstore.model.ShippingInfo;

public class OrderRowMapper implements RowMapper<Order>{

	public Order mapRow(ResultSet rs, int arg1) throws SQLException {		
		Order order = new Order();
		
		Client c = new Client();
		c.setId(rs.getInt("b.idclient"));
		c.setName(rs.getString("b.name"));
		c.setEmail(rs.getString("b.email"));
		c.setRole(rs.getString("b.role"));
		order.setClient(c);
		
		order.setDateCreate(rs.getDate("datecreate"));
		order.setId(rs.getInt("a.id"));
		order.setStatus(rs.getString("a.status"));
		order.setPaymentMethod(rs.getString("a.paymentmethod"));
		
		ShippingInfo shippingInfo = new ShippingInfo();
		shippingInfo.setReceiverName(rs.getString("c.receivername"));
		shippingInfo.setNumber(rs.getString("c.number"));
		shippingInfo.setId(rs.getInt("c.id"));
		shippingInfo.setDistrict(rs.getString("c.district"));
		shippingInfo.setCity(rs.getString("c.city"));
		shippingInfo.setZipcode(rs.getString("c.zipcode"));
		order.setShippingInfo(shippingInfo);
		return order;
	}

}
