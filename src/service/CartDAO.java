package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.CartItem;


public class CartDAO {
	
	
	public boolean addCartItem(int customerID, String productName, String supplierName,
			int quantity) throws SQLException {
		Connection conn = Database.getInstance().getConnection();
		
		CallableStatement cstmt = 
				conn.prepareCall
				("{call add_cart_item(?,?,?,?,?)} ");
		
		cstmt.setInt(1, customerID);
		cstmt.setString(2, productName);
		cstmt.setString(3, supplierName);
		cstmt.setInt(4, quantity);
		cstmt.registerOutParameter(5, java.sql.Types.INTEGER);;
		
		cstmt.execute();
		
		boolean insufficientStock = cstmt.getBoolean(5);
		
		cstmt.close();
		
		return insufficientStock;
	}
	
	
	public void clearCart(int CustomerID) throws SQLException
	{
		
			Connection conn = Database.getInstance().getConnection();
			
			PreparedStatement pstmt = 
					conn.prepareStatement("delete from cart_item where customer_id = ?");
			
			pstmt.setInt(1, CustomerID);	
			
			pstmt.executeUpdate();
			
			pstmt.close();
		
	}


	public List<CartItem> getCartItems(int customerID) throws SQLException {
		List<CartItem> cartItemList = new ArrayList<>();
		
		Connection conn = Database.getInstance().getConnection();
		
		PreparedStatement p = 
				conn.prepareStatement("select p.name,s.name,ci.quantity,ci.item_price_after_discount,"
						+ "ci.total_price_after_discount "
						+ "from cart_item ci "
						+ "inner join supplier_product sp"
						+ 			" on ci.supplier_product_id = sp.id"
						+ " inner join product p on sp.product_id = p.id"
						+ " inner join supplier s on sp.supplier_id = s.id"
						+ " where customer_id = ?");
		 
		p.setInt(1, customerID);
		ResultSet rs = p.executeQuery();
		while(rs.next())
		{
			CartItem ci = new CartItem(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getFloat(5));
			cartItemList.add(ci);
		}
		return cartItemList;
	}
	

}
