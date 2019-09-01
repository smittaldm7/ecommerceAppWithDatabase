package controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.CartDAO;
import service.CustomerDAO;
import service.KeywordDAO;
import service.OrderDAO;
import service.OrderItemDAO;
import service.ProductDAO;
import service.ProductKeywordDAO;
import service.SupplierDAO;
import service.SupplierProductDAO;
import model.CartItem;
import model.Customer;
import model.Keyword;
import model.Order;
import model.OrderItem;
import model.Product;
import model.ProductKeyword;
import model.Supplier;
import model.SupplierProduct;




public class ECommerceMarket {


	public int currentCustomerID=1; //changing to 1 so I don't have to login each time while testing
	//public int currentCustomerID=0;
	
	public void addKeyword(String text) throws SQLException 
	{
		KeywordDAO keywordDAO = new KeywordDAO();
		keywordDAO.addKeyword(new Keyword(text));
		
	}
	

	public void addProduct(String name) throws SQLException
	{
		ProductDAO productDAO = new ProductDAO();
		productDAO.addProduct(new Product(name));
		
	}
	
	public void addProductKeyword(String name, List<String> keywords) throws SQLException {
		
		ProductDAO productDAO = new ProductDAO();
		int productID = productDAO.getProductIDFromName(name);
		
		KeywordDAO keywordDAO = new KeywordDAO();
		ProductKeywordDAO productKeywordDAO = new ProductKeywordDAO();
		for (String keyword:keywords)
		{
			int keywordID = keywordDAO.getKeywordIDFromText(keyword);
			productKeywordDAO.addProductKeyword(new ProductKeyword(productID, keywordID));
		}
		
	}
	
	public void addSupplier(String name, String address) throws SQLException
	{
		//suppliers.add(new Supplier(name, address));
		SupplierDAO supplierDAO = new SupplierDAO();
		supplierDAO.addSupplier(new Supplier(name,address));
	}
	
	public void addSupplierProduct(int supplierId, int productId, int stock, 
			float price, float discountPercent) throws SQLException
	{
		SupplierProduct supplierProduct = 
				new SupplierProduct(supplierId, productId, price, discountPercent, stock);
		SupplierProductDAO supplierProductDAO = new SupplierProductDAO();
		supplierProductDAO.addSupplierProduct(supplierProduct);
		

	}
	
	public void addCustomer(String name, String address, long phoneNumber,
			String email, String password) throws SQLException
	{
		Customer customer = new Customer(name,address,phoneNumber,email,password);
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.addCustomer(customer);

	}
	
	
	public List<Product> searchForProductWithText(String searchString) throws SQLException
	{
		List<Product> favourableProducts = new ArrayList<Product>();
		//search the Products and return favourable products
		//use SupplierProductDAO
		//method retrieve
		
		ProductKeywordDAO productKeywordDAO = new ProductKeywordDAO();
		favourableProducts = productKeywordDAO.getProductFromSearchString(searchString);
		
		return favourableProducts;
	}
	
	public boolean addCartItem(String productName, 
			String supplierName, int quantity) throws SQLException 
	{
		CartDAO cartDAO = new CartDAO();
		boolean insufficientStock = cartDAO.addCartItem
							(currentCustomerID, productName, supplierName, quantity);
		return insufficientStock;
			
		
	}
	
	public Map<String, Boolean> placeOrder() throws SQLException {
		Map<String, Boolean> map= new HashMap<>();
		OrderDAO orderDAO = new OrderDAO();
		map = orderDAO.addOrder(currentCustomerID);
		return map;
		
	}
	
	
	public void login(String email, String password) throws SQLException
	{
		
		CustomerDAO customerDAO = new CustomerDAO();
		int customerID = customerDAO.getCustomerId(email,password);
		if(customerID!=0)
			{
			currentCustomerID=customerID;
			System.out.println("Login successful");
			}
		else
			{
			System.out.println("Login unsuccessful");
			}
		
	}

	public void clearCart() throws SQLException
	{
		CartDAO cartDAO = new CartDAO();
		cartDAO.clearCart(currentCustomerID);
	}

	public List<CartItem> viewCartItems() throws SQLException 
	{
		CartDAO cartDAO = new CartDAO();
		List<CartItem> cartItemList = cartDAO.getCartItems(currentCustomerID);
		return cartItemList;
	}
	
	
	public Customer viewCustomerDetails() throws SQLException 
	{
		CustomerDAO customerDAO = new CustomerDAO();
		Customer customer =customerDAO.getCustomer(currentCustomerID);
		return customer;
	}
	
	
	public List<SupplierProduct> getProductDetails(String productName) throws SQLException {
		ProductDAO productDAO = new ProductDAO();
		return productDAO.getProductSuppliers(productName);
		
		
	}

	public List<OrderItem> getOrderItems(int orderID) throws SQLException
	{
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		List<OrderItem> orderItemList= orderItemDAO.getOrderItems(currentCustomerID, orderID);
		return orderItemList;
	}

	public List<Order> getOrders() throws SQLException {
		OrderDAO orderDAO = new OrderDAO();
		List<Order> orderList = orderDAO.getOrders(currentCustomerID);
		return orderList;
	}


	public List<Product> viewProducts() throws SQLException {
		ProductDAO productDAO = new ProductDAO();
		List<Product> productList = productDAO.getProducts();
		return productList;
	}
	
	
	



	

	


	
	

}
