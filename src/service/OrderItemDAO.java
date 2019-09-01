package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT supplier_product_id,\r\n" + 
				"		quantity,\r\n" + 
				"		item_price_after_discount,\r\n" + 
				"		total_price_after_discount  FROM order_item WHERE order_id = ?");
		
		pstmt.setInt(1,orderID);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
			{
			OrderItem orderItem = new OrderItem(rs.getInt(1), rs.getInt(2),
							rs.getFloat(3), rs.getFloat(4)
							);
			orderItemList.add(orderItem);
			}
			
		pstmt.close();
		
		return orderItemList;
	}

}
