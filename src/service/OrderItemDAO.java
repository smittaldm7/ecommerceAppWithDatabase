package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.OrderItem;

public class OrderItemDAO {
	
	public List<OrderItem> getOrderItems(int customerID, int orderID) throws SQLException
	{
		List<OrderItem> orderItemList = new ArrayList<>();

		Connection conn = Database.getInstance().getConnection();
		
		CallableStatement cstmt = conn.prepareCall("{call get_order_items(?)}");
		
		cstmt.setInt(1,orderID);
		
		cstmt.execute();
		
		ResultSet rs = cstmt.getResultSet();
		
		while(rs.next())
			{
			OrderItem orderItem = new OrderItem(rs.getString(1), rs.getString(2),
							rs.getInt(3), rs.getFloat(4)
							);
			orderItemList.add(orderItem);
			}
			
		cstmt.close();
		
		return orderItemList;
	}

}
