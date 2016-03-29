package package1;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.CartItem;
import model.Customer;
import model.Database;
import model.Order;
import model.OrderItem;
import model.Product;
import model.SupplierProduct;


public class App {
	static ECommerceMarket amazon= new ECommerceMarket();
	public static void main(String[] args) throws IOException {
	
		try 
		{
			Database.getInstance().connect();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		addDataToDatabase();
		//readDataFromFileAndAddtoDatabase();
		while(true)
		{
			System.out.println("Welcome to the E-Commerce Market");	
			
			System.out.println("1. View All Products");
			System.out.println("2. Search for a Product");//done
			System.out.println("3. Add Product to Cart");//done
			System.out.println("4. View Items in Cart"); //done
			System.out.println("5. Place Order");//done
			System.out.println("7. View Product's Suppliers and details");//done
			System.out.println("8. Login");//done
			System.out.println("9. View Customer Details");//done
			System.out.println("10. View Orders");//done
			System.out.println("11. Clear cart");//done
			System.out.println("15. Exit");
			
			System.out.println("enter option:");
			Scanner input = new Scanner(System.in);
			int option = input.nextInt();
			switch(option)
			{
			case 1:
				List<Product> products=null;
				try 
				{
					products = amazon.viewProducts();
				} 
				catch (SQLException e3) 
				{
					e3.printStackTrace();
				}
				for(Product p : products)
					System.out.println(p.getName());
				System.in.read();
				break;
				
			case 2:
				System.out.println("enter keyword for product search:");
				Scanner input2 = new Scanner(System.in);
				String searchText = input2.nextLine();
				List<Product> favourableProducts = null;
				try 
				{
					favourableProducts = amazon.searchForProductWithText(searchText);
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
				for(Product p:favourableProducts)
					{
					System.out.println(p);
					}
				System.in.read();
				break;
				
			case 3: 
				if(amazon.currentCustomerID==0)
				{
					System.out.println("Not logged in");
				}
				else
				{
					System.out.println("Enter product name:");
					Scanner input4 = new Scanner(System.in);
					String productName=input4.nextLine();
					System.out.println(productName);
					
					System.out.println("Enter supplier name:");
					input4 = new Scanner(System.in);
					String supplierName = input4.nextLine();
					System.out.println(supplierName);
					
					System.out.println("Enter quantity:");
					input4 = new Scanner(System.in);
					int quantity = input4.nextInt();
					if(quantity<=0)
					{
						System.out.println("enter an appropriate quantity");
					}
					else
					{
						try 
						{
							boolean insufficientStock =
									amazon.addCartItem(productName, supplierName, quantity);
							if(insufficientStock)
								System.out.println("Not enough stock");
							else
								System.out.println("Added to cart successfully");
						} 
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
					}
				}
				System.in.read();
				break;
			case 4:
				if(amazon.currentCustomerID==0)
				{
					System.out.println("Not logged in");
				}
				else
				{
					try 
					{
						List<CartItem> cartItemList = amazon.viewCartItems();
						for(CartItem ci:cartItemList)
						{
							System.out.println(ci);
						}
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
				}
				
				System.in.read();
				break;
			case 5:
				if(amazon.currentCustomerID==0)
					{
					System.out.println("Not logged in");
					}
				else
				{
					Map<String, Boolean> map = new HashMap<>();
					try {
						map = amazon.placeOrder();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(map.get("cartEmpty")==true )
					{
						System.out.println("cart is empty");
					}
					
					if(map.get("insufficientStock")==true)
					{
						System.out.println("insufficient stock of one or more items in cart");
					}
					else
					{
						System.out.println("Order placed");
					}
				}
				System.in.read();
				break;
			
				
			case 7:
				
				
				System.out.println("Enter product name:");
				Scanner input3 = new Scanner(System.in);
				String productName=input3.nextLine();
				
				List<SupplierProduct> productSuppliersList = null;
				
				try 
				{
					productSuppliersList = amazon.getProductDetails(productName);
				} 
				catch (SQLException e2) 
				{
					e2.printStackTrace();
				}
				for(SupplierProduct sp:productSuppliersList)
				{
					System.out.println(sp);
				}
				System.in.read();
				break;
				
			
			case 8:
				System.out.println("Enter login");
				Scanner input5 = new Scanner(System.in);
				String email = input5.nextLine();
				
				input5 = new Scanner(System.in);
				String password = input5.nextLine();
				
				try 
				{
					amazon.login(email, password);
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
				System.in.read();
				break;
				
			case 9:
				if(amazon.currentCustomerID==0)
				{
					System.out.println("Not logged in");
				}
				else
				{
					Customer customer=null;
					try 
					{
						customer = amazon.viewCustomerDetails();
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
					System.out.println(customer.view());
				}
				System.in.read();
				break;
			
			case 10:
				if(amazon.currentCustomerID==0)
				{
					System.out.println("Not logged in");
				}
				else
				{
					List<Order> orders = null;
					try 
					{
						orders = amazon.getOrders();
						for (Order order:orders)
						{
							List<OrderItem> orderItems=amazon.getOrderItems(order.getId());
							System.out.println(order.view());
							for (OrderItem orderItem:orderItems)
							{
								System.out.println("\t"+orderItem);
							}
							
						}
						
					}
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
				}	
				System.in.read();
				break;
			
			case 11:
				if(amazon.currentCustomerID==0)
				{
					System.out.println("Not logged in");
				}
				else
				{
					try 
					{
						amazon.clearCart();
						System.out.println("Cart has been cleared");
					} catch (SQLException e) 
					{
						e.printStackTrace();
					}
				}
				System.in.read();
				break;
				
			case 15:
				System.exit(0);
				
			}
			
		}
		
		
		

		

	}
	
	
	/*
	private static void readDataFromFileAndAddtoDatabase() {
		readProducts();
		readSuppliers();
		readSupplierProducts();
		readCustomers();
		
	}


	private static void readProducts() 
	{
		Scanner s=null;
		try {
			s = new Scanner(new File("products"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine())
		{
			String line =s.nextLine();
			//System.out.println(line);
			String[] splitLine = line.split(";");
			List<Object> splitLineList = new ArrayList<>();
			int i=1;
			for(String st:splitLine)
				{
				switch(i)
					{
					case 1:
						String name= st;
						splitLineList.add(st);
						break;
					
					case 2:
						String[] keywords = st.split(",");
						List<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));
						splitLineList.add(keywordsList);
						break;
					}
				i++;
				}

			try 
			{
				amazon.addProduct((String)splitLineList.get(0), 
						(List<String>)splitLineList.get(1));
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	private static void readSuppliers() {
		Scanner s=null;
		try 
		{
			s = new Scanner(new File("suppliers"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine())
		{
			String line =s.nextLine();
			System.out.println(line);
			String[] splitLine = line.split(";");
			List<String> splitLineList = new ArrayList<String>(Arrays.asList(splitLine));
			System.out.println(splitLineList);
			
			try {
				amazon.addSupplier(splitLineList.get(0), splitLineList.get(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	private static void readSupplierProducts() {
		Scanner s=null;
		try 
		{
			s = new Scanner(new File("productsupplier"));
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine())
		{
			String line =s.nextLine();
			//System.out.println(line);
			String[] splitLine = line.split(";");
			List<Integer> splitLineListInt = new ArrayList<Integer>();
			for(String ss:splitLine)
			{
				splitLineListInt.add(Integer.valueOf(ss.trim()));
			}
					
			try 
			{
				amazon.addSupplierProduct(splitLineListInt.get(0),	splitLineListInt.get(1),
						splitLineListInt.get(2),splitLineListInt.get(3),
						splitLineListInt.get(4));
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	private static void readCustomers() {
		Scanner s=null;
		try 
		{
			s = new Scanner(new File("customers"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(s.hasNextLine())
		{
			String line =s.nextLine();
			//System.out.println(line);
			String[] splitLine = line.split(";");
			List<Object> splitLineList = new ArrayList<>();
			int i=0;
			for(String ss:splitLine)
			{
				
				if(i==2)
					splitLineList.add(Long.valueOf(ss.trim()));
				else
					splitLineList.add(ss.trim());
				i++;
			}
			
			System.out.println(splitLineList.get(3).getClass());
		
			try 
			{
				amazon.addCustomer((String)splitLineList.get(0), (String)splitLineList.get(1),
						(Long)splitLineList.get(2),(String)splitLineList.get(3),
						(String)splitLineList.get(4));
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	*/
	
	static void addDataToDatabase()
	{
		addSuppliersToMarket();
		addProductsandKeywordsToMarket();
		addSupplierProductToMarket();
		addCustomer();
	}
	
	

	static void addProductsandKeywordsToMarket()
	{
		try 
		{
			amazon.addKeyword("shoes");
			amazon.addKeyword("puma");
			amazon.addKeyword("running");
			
			amazon.addProduct("Puma Running Shoes");
			
			List<String> keywords = new ArrayList<String>();
			keywords.add("shoes");
			keywords.add("puma");
			keywords.add("running");
			amazon.addProductKeyword("Puma Running Shoes", keywords);
			
			
			amazon.addKeyword("laptop");
			amazon.addKeyword("dell");
			amazon.addKeyword("computer");
			amazon.addKeyword("inspiron");
			
			amazon.addProduct("Dell Inspiron Laptop");
			
			keywords.clear();
			keywords.add("laptop");
			keywords.add("dell");
			keywords.add("computer");
			keywords.add("inspiron");
			amazon.addProductKeyword("Dell Inspiron Laptop", keywords);
			
			
			amazon.addKeyword("pigeon");
			amazon.addKeyword("stove");
			amazon.addKeyword("cooking");
			amazon.addKeyword("gas");
			
			amazon.addProduct("Pigeon Gas Stove");
			
			keywords.clear();
			keywords.add("pigeon");
			keywords.add("stove");
			keywords.add("cooking");
			keywords.add("gas");
			amazon.addProductKeyword("Pigeon Gas Stove", keywords);
			
			
			amazon.addKeyword("LG");
			amazon.addKeyword("190");
			amazon.addKeyword("fridge");
			amazon.addKeyword("refrigerator");
			
			amazon.addProduct("LG 190L Refrigerator");
			
			keywords.clear();
			keywords.add("LG");
			keywords.add("190");
			keywords.add("fridge");
			keywords.add("refrigerator");
			amazon.addProductKeyword("LG 190L Refrigerator", keywords);
			
		} 

		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addSuppliersToMarket()
	{
		try {
			amazon.addSupplier("Guptas", "#101, MG Road, Bangalore");
			amazon.addSupplier("Mehras", "#12, 5th Block Kormangala, Bangalore");
			amazon.addSupplier("Reddys", "#322, JC Road, Bangalore");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addSupplierProductToMarket()
	{
		try 
		{
			amazon.addSupplierProduct("Guptas", "Puma Running Shoes", 5, 3000, 0);
			amazon.addSupplierProduct("Mehras", "Puma Running Shoes" , 1, 2800, 0);
			amazon.addSupplierProduct("Mehras", "Dell Inspiron Laptop", 5, 20000, 0);
			amazon.addSupplierProduct("Reddys", "Dell Inspiron Laptop", 10, 18000, 0);
			amazon.addSupplierProduct("Mehras", "Pigeon Gas Stove", 5, 2000, 0);
			amazon.addSupplierProduct("Mehras", "LG 190L Refrigerator", 50, 10000, 10);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		
		
	}
	private static void addCustomer() {
		try {
			amazon.addCustomer("sidharth", "#1 NGV Koramangala", 22067130,
					"smittaldm7@gmail.com", "sid123");
			amazon.addCustomer("Ramona", "#5 MG Road", 22023430,
					"email.vineetmittal@gmail.com", "vin123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
