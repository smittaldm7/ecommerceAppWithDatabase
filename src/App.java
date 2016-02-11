import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class App {
	static ECommerceMarket amazon= new ECommerceMarket();
	public static void main(String[] args) throws IOException {
	
		//addData();
		readDataFromFile();
		while(true)
		{
			System.out.println("Welcome to the E-Commerce Market");	
			
			System.out.println("1. List All Products");
			System.out.println("2. Search for a Product");
			System.out.println("3. Add Product to Cart");
			System.out.println("4. View Items in Cart"); 
			System.out.println("5. Place Order");
			System.out.println("6. View All Suppliers");
			System.out.println("7. View Product");
			System.out.println("8. Login");
			System.out.println("9. View Customer Details");
			System.out.println("10. View Order History");
			System.out.println("15. Exit");
			
			System.out.println("enter option:");
			Scanner input = new Scanner(System.in);
			int option = input.nextInt();
			switch(option)
			{
			case 1:
				for(Product p:amazon.products)
					{
					System.out.println(p);
					}
				System.in.read();
				break;
			case 2:
				System.out.println("enter keyword for product search:");
				Scanner input2 = new Scanner(System.in);
				String searchText = input2.nextLine();
				
				List<Product> favourableProducts = amazon.findProduct(searchText);
				
				for(Product p:favourableProducts)
					{
					System.out.println(p);
					}
				System.in.read();
				break;
				
			case 3: 
				if(amazon.currentCustomer==null)
				{
					System.out.println("Not logged in");
					System.in.read();
					break;
					}
				boolean productIdExists=false;
				System.out.println("Enter product id:");
				Scanner input4 = new Scanner(System.in);
				int pId=input4.nextInt();
				
				System.out.println("Enter supplier id:");
				input4 = new Scanner(System.in);
				int supplierId = input4.nextInt();
				
				System.out.println("Enter quantity:");
				input4 = new Scanner(System.in);
				int quantity = input4.nextInt();
				
				amazon.addToCart(pId, supplierId, quantity);
				
				System.in.read();
				break;
			case 4:
				if(amazon.currentCustomer==null)
				{
					System.out.println("Not logged in");
					System.in.read();
					break;
					}
				amazon.viewCartItems();
				
				System.in.read();
				break;
			case 5:
				if(amazon.currentCustomer==null)
					{
					System.out.println("Not logged in");
					System.in.read();
					break;
					}
				
				amazon.placeOrder();
				System.in.read();
				break;
			
				
			case 6:
				amazon.viewSuppliers();
				System.in.read();
				break;
			case 7:
				boolean productExists=false;
				System.out.println("Enter product id:");
				Scanner input3 = new Scanner(System.in);
				int prodId=input3.nextInt();
				
				for(Product p:amazon.products)
				{
					if(prodId==p.id)
						{
						productExists=true;
						}
				}
				if(!productExists)
				{
					System.out.println("product id does not exist");
				}
				amazon.viewProduct(prodId);
				System.in.read();
				break;
				
			case 8:
				System.out.println("Enter login");
				Scanner input5 = new Scanner(System.in);
				String email = input5.nextLine();
				
				input5 = new Scanner(System.in);
				String password = input5.nextLine();
				amazon.login(email, password);
				
				System.in.read();
				break;
				
			case 9:
				if(amazon.currentCustomer==null)
				{
					System.out.println("Not logged in");
					System.in.read();
					break;
				}
				System.out.println(amazon.viewCustomerDetails());
				System.in.read();
				break;
			
			case 10:
				if(amazon.currentCustomer==null)
				{
					System.out.println("Not logged in");
					System.in.read();
					break;
				}
				System.out.println(amazon.viewOrders());
				System.in.read();
				break;
				
			case 15:
				System.exit(0);
				
			}
			
		}
		
		
		

		

	}
	
	

	private static void readDataFromFile() {
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

			amazon.addProduct((String)splitLineList.get(0), (List<String>)splitLineList.get(1));
			
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
			
			amazon.addSupplier(splitLineList.get(0), splitLineList.get(1));
		}
		
	}


	private static void readSupplierProducts() {
		Scanner s=null;
		try 
		{
			s = new Scanner(new File("productsupplier"));
		} catch (FileNotFoundException e) {
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
					
			amazon.addSupplierProduct(splitLineListInt.get(0),	splitLineListInt.get(1),
					splitLineListInt.get(2),splitLineListInt.get(3),splitLineListInt.get(4));
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
		
			amazon.addCustomer((String)splitLineList.get(0), (String)splitLineList.get(1),
					(Long)splitLineList.get(2),(String)splitLineList.get(3),(String)splitLineList.get(4));
		}
		
	}

	static void addData()
	{
		addSuppliersToMarket();
		addProductsToMarket();
		addSupplierProductToMarket();
		addCustomer();
	}
	
	

	static void addSuppliersToMarket()
	{
		amazon.addSupplier("Guptas", "#101, MG Road, Bangalore");
		amazon.addSupplier("Mehras", "#12, 5th Block Kormangala, Bangalore");
		amazon.addSupplier("Reddys", "#322, JC Road, Bangalore");
	}
	static void addProductsToMarket()
	{
		List<String> keywords = new ArrayList<String>();
		keywords.add("shoes");
		keywords.add("puma");
		keywords.add("running");
		amazon.addProduct("Puma Running Shoes", keywords);
		keywords = new ArrayList<String>();
		keywords.add("laptop");
		keywords.add("dell");
		keywords.add("computer");
		keywords.add("inspiron");
		amazon.addProduct("Dell Inspiron Laptop", keywords);
		keywords = new ArrayList<String>();
		keywords.add("pigeon");
		keywords.add("stove");
		keywords.add("cooking");
		keywords.add("gas");
		amazon.addProduct("Pigeon Gas Stove", keywords );
		keywords = new ArrayList<String>();
		keywords.add("LG");
		keywords.add("190");
		keywords.add("fridge");
		keywords.add("refrigerator");
		amazon.addProduct("LG 190L Refrigerator", keywords);
	}
	
	static void addSupplierProductToMarket()
	{
		amazon.addSupplierProduct(1, 1, 5, 3000, 0);
		amazon.addSupplierProduct(1, 2 , 1, 2800, 0);
		amazon.addSupplierProduct(2, 2, 5, 20000, 0);
		amazon.addSupplierProduct(2, 3, 10, 18000, 0);
		amazon.addSupplierProduct(3, 2, 5, 2000, 0);
		amazon.addSupplierProduct(4, 2, 50, 10000, 10);
		
		
		
		
	}
	private static void addCustomer() {
		amazon.addCustomer("sidharth", "#1 NGV Koramangala", 22067130,
				"smittaldm7@gmail.com", "sid123");
		amazon.addCustomer("Ramona", "#5 MG Road", 22023430,
				"email.vineetmittal@gmail.com", "vin123");
		
	}

}
