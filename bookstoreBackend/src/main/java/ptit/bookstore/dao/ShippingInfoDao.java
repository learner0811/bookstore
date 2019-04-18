package ptit.bookstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ptit.bookstore.model.ShippingInfo;

@Repository
public class ShippingInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	public int addShippingInfo(final ShippingInfo shippingInfo)
	{
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into shippinginfo "
						+ "(receiverName, number, district, city, zipcode)"
						+ " values (?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, shippingInfo.getReceiverName());
				ps.setString(2, shippingInfo.getNumber());
				ps.setString(3, shippingInfo.getDistrict());
				ps.setString(4, shippingInfo.getCity());
				ps.setString(5, shippingInfo.getZipcode());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
}
