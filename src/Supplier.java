import java.util.ArrayList;
import java.util.List;


public class Supplier {
	static int idCounter=0;;
	int id;
	String name;
	List<SupplierProduct> productList = new ArrayList<SupplierProduct>();
	String warehouseAddress;
	
	Supplier(String name, String address)
	{
		id = ++idCounter;
		this.name=name;
		this.warehouseAddress=address;
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", productList="
				+ productList + ", warehouseAddress=" + warehouseAddress + "]";
	}


}
