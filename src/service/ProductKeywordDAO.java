package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Database;
import model.Product;
import model.ProductKeyword;



public class ProductKeywordDAO {

	public int addProductKeyword(ProductKeyword productKeyword) throws SQLException 
	{
	Connection conn = Database.getInstance().getConnection();

	PreparedStatement p = conn
			.prepareStatement("insert into product_keyword_relationship ( Product_Id,"
					+ "Keyword_Id) values (?,?)");

	p.setInt(1, productKeyword.getProductID());
	p.setInt(2, productKeyword.getKeywordID());
	
	int status = p.executeUpdate();

	p.close();
	
	return status;
	
	}

	public List<Product> getProductFromSearchString(String searchString) throws SQLException {
		
		///For no reason we choose to do via stored procedure instead of prepared statement
		Connection conn = Database.getInstance().getConnection();
		
		List<Product> favourableProducts = new ArrayList<Product>();
		
		CallableStatement cstmt = conn.prepareCall("{call get_products_from_search_string(?)}");

		cstmt.setString(1, searchString);
				
		cstmt.execute();
		
		ResultSet rs = cstmt.getResultSet();
		
		while(rs.next())
		{
			Product p = new Product(rs.getString("name"));
			favourableProducts.add(p);
		}
		
		cstmt.close();
		
		return favourableProducts;
		
		
	}

}
