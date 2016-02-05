import java.util.ArrayList;
import java.util.List;


public class Product {
	static int idCounter=0;
	int id;
	String name;
	float price;
	public float getPrice() {
		return price;
	}
	List<String> keywords = new ArrayList<String>();
	@Override
	public String toString() {
		return "Product [id=" + id + ",  name=" + name + ", price=" + price + ", keywords="
				+ keywords + "]";
	}
	public Product(String name, float price, List<String> keywords) {
		super();
		
		this.id=++idCounter;
		this.name = name;
		this.price = price;
		this.keywords = keywords;
	}
	
}
