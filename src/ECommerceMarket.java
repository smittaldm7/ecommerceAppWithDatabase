import java.util.ArrayList;
import java.util.List;




public class ECommerceMarket {

	List<Product> products = new ArrayList<Product>();
	Cart cart = new Cart();
	List<Order> orders = new ArrayList<Order>();
	void addProduct(String name, float price, List<String> keywords)
	{
		products.add(new Product(name,price, keywords));
	}
	
	List<Product> findProduct(String searchString)
	{
		List<Product> favourableProducts = new ArrayList<Product>();
		for(Product p:products)
		{
			if(p.keywords.contains(searchString))
			{
				favourableProducts.add(p);
			}
		}
		return favourableProducts;
	}
	
	
	

}
