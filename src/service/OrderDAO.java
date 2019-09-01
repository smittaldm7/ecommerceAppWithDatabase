package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.Database;
import model.Order;

public class OrderDAO {
	

	public Map<String, Boolean> addOrder(int currentCustomerID) throws SQLException {
		Connection conn = Database.getInstance().getConnection();
		
		CallableStatement cstmt = 
				conn.prepareCall
				("{call add_order(?,?,?)} ");
		
		cstmt.setInt(1, currentCustomerID);
		cstmt.registerOutParameter(2, java.sql.Types.BOOLEAN);;
		cstmt.registerOutParameter(2, java.sql.Types.BOOLEAN);;
		cstmt.execute();
		
		boolean cartEmpty = cstmt.getBoolean(2);
		boolean insufficientStock = cstmt.getBoolean(3);
		
		cstmt.close();
		//return a map to return multiple values from this method
		Map<String, Boolean> map= new HashMap<>();
		map.put("cartEmpty",cartEmpty);
		map.put("insufficientStock", insufficientStock);
		//System.out.println(""+cartEmpty+insufficientStock);
		return map;
	}

	public List<Order> getOrders(int customerID) throws SQLException {
		List<Order> orders = new ArrayList<>();
		
		Connection conn = Database.getInstance().getConnection();
		
		PreparedStatement ps = conn.prepareStatement
				("select id, date, total_bill_amount from order_table where customer_id = ?");
		
		ps.setInt(1, customerID);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			Order order = new Order(rs.getInt(1), rs.getTimestamp(2), rs.getFloat(3));
			orders.add(order);
		}
		return orders;
		
	}
	
	

}
