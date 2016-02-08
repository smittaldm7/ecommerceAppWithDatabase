import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class App {
	static ECommerceMarket amazon= new ECommerceMarket();
	public static void main(String[] args) throws IOException {
	
		addProductsToMarket();
		while(true)
		{
			System.out.println("Welcome to the E-Commerce Market");	
			
			System.out.println("1. List All Products");
			System.out.println("2. Search for a Product");
			System.out.println("3. Add Product to Cart");
			System.out.println("4. View Items in Cart"); 
			System.out.println("5. Place Order");
			System.out.println("6. Exit");
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
				System.out.println("Enter product id:");
				Scanner input3 = new Scanner(System.in);
				int productId = input3.nextInt();
				
				System.out.println("Enter quantity:");
				Scanner input4 = new Scanner(System.in);
				int productQuantity = input3.nextInt();
				
				amazon.cart.addItem(productId, productQuantity);
				break;
			case 4:
				for(CartItem ci:amazon.cart.viewItems())
				{
					System.out.println(ci);
				}
				System.in.read();
				break;
			case 5:
				Order order = new Order(amazon.cart.cartItems,new Date());
				amazon.orders.add(order);
				System.out.println(order);
				System.in.read();
				break;
			case 6:
				System.exit(0);
				
			}
			
		}
		
		
		
		
		

	}
	static void addProductsToMarket()
	{
		List<String> keywords = new ArrayList<String>();
		keywords.add("shoes");
		keywords.add("puma");
		keywords.add("running");
		amazon.addProduct("Puma Running Shoes", 3500.00f, keywords);
		keywords = new ArrayList<String>();
		keywords.add("laptop");
		keywords.add("dell");
		keywords.add("computer");
		keywords.add("inspiron");
		amazon.addProduct("Dell Inspiron Laptop", 4500.00f,keywords);
		keywords = new ArrayList<String>();
		keywords.add("pigeon");
		keywords.add("stove");
		keywords.add("cooking");
		keywords.add("gas");
		amazon.addProduct("Pigeon Gas Stove", 2000f,keywords );
		keywords = new ArrayList<String>();
		keywords.add("LG");
		keywords.add("190");
		keywords.add("fridge");
		keywords.add("refrigerator");
		amazon.addProduct("LG 190L Refrigerator", 13500f, keywords);
	}

}
