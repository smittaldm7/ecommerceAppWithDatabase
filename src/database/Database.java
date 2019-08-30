package database;

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
	
	Connection conn;
	
	public Connection getConnection() {
		return conn;
	}
	
	
	public void connect() throws Exception {
		if (conn != null)
			return;

		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) 
			{
			throw new Exception("Driver not found");
			}

		String url = String.format("jdbc:mysql://localhost:%d/ecommerce_console_app_db", 3306);
		conn = DriverManager.getConnection(url, "root", "");
	}
	
	public void disconnect() {
		if (conn != null) 
		{
			try 
			{
				conn.close();
			} catch (SQLException e) 
				{
				System.out.println("Can't close connection");
				}
		}
		conn = null;
	}
}
