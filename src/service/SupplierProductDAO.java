package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.SupplierProduct;




public class SupplierProductDAO {

	public void addSupplierProduct(SupplierProduct supplierProduct) throws SQLException {

		Connection conn = Database.getInstance().getConnection();

		CallableStatement c = conn
				.prepareCall("{call addSupplierProduct(?,?,?,?,?)}");

		c.setString(1, supplierProduct.getSupplierName());
		c.setString(2, supplierProduct.getProductName());
		c.setFloat(3, supplierProduct.getPrice());
		c.setFloat(4, supplierProduct.getDiscountPercent());
		c.setInt(5,supplierProduct.getStock());
		
		c.execute();

		c.close();
		
	}
}
