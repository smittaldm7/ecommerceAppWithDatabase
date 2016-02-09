import java.util.ArrayList;
import java.util.List;


public class Customer {

	String name;
	String address;
	long phoneNumber;
	String email;
	String password;
	Cart cart = new Cart();;
	List<Order> orders = new ArrayList<Order>();
	
	public Customer(String name, String address, long phoneNumber,
			String email, String password) 
	{
		super();
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		
	}
	//delete()
	//edit()
	void login(String email, String password)
	{
		
	}
	//void placeOrder() defined in ECommerceMarket class
	{
		
	}
	//
	String viewOrders()
	{
		return orders.toString();
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", password=" + password + ", cart=" + cart + ", orders="
				+ orders + "]";
	}
	public String viewDetails() {
		return "Customer [name=" + name + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", email=" + email
				 + ", cart=" + cart + ", orders="
				+ orders + "]";
	}
	

}
