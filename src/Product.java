import java.util.ArrayList;
import java.util.List;


public class Product {
	static int idCounter=0;
	int id;
	String name;

	List<SupplierProduct> supplierList = new ArrayList<SupplierProduct>();
	List<String> keywords = new ArrayList<String>();
	
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", supplierList="
				+ supplierList + ", keywords=" + keywords + "]";
	}
	
	public Product(String name, List<String> keywords) {
		super();
		
		this.id=++idCounter;
		this.name = name;
		this.keywords = keywords;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}
	

	

	
	
}
