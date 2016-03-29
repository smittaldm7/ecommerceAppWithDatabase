package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//singleton class
public class Database {

	private Database()
	{
		
	}
	
	static private Database instance = new Database();
	
	static public Database getInstance()
	{
		return instance;
	}
	
	Connection con;
	
	public Connection getConnection() {
		return con;
	}
	
	
	public void connect() throws Exception {
		if (con != null)
			return;
		System.out.println("lulu");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}

		System.out.println("lulu2");
		String url = String.format("jdbc:mysql://localhost:%d/ecommerce", 3306);
		System.out.println("lulu3");
		con = DriverManager.getConnection(url, "root", "password");
		System.out.println("lulu4");
	}
	
	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
		
		con = null;
	}
}
