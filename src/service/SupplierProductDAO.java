package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Database;
import model.SupplierProduct;




public class SupplierProductDAO {

	public void addSupplierProduct(SupplierProduct supplierProduct) throws SQLException {

		///For no reason we choose to do via stored procedure instead of prepared statement
		Connection conn = Database.getInstance().getConnection();

		CallableStatement c = conn
				.prepareCall("{call add_supplier_product(?,?,?,?,?)}");

		c.setInt(1, supplierProduct.getSupplierId());
		c.setInt(2, supplierProduct.getProductId());
		c.setFloat(3, supplierProduct.getPrice());
		c.setFloat(4, supplierProduct.getDiscountPercent());
		c.setInt(5,supplierProduct.getStock());
		
		c.execute();

		c.close();
		
	}
}
