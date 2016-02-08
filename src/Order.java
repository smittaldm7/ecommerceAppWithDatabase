import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order {
	List<CartItem> OrderItems = new ArrayList<CartItem>();
	float totalBillAmount=0;
	Date date;
	public Order(List<CartItem> orderItems,  Date date) {
		super();
		OrderItems = orderItems;
		this.date = date;
		
		for (CartItem oi :OrderItems)
		{
			totalBillAmount+=oi.priceAfterDiscount;	
			
		}
		
	}
	@Override
	public String toString() {
		return "Order [OrderItems=" + OrderItems + ", totalBillAmount="
				+ totalBillAmount + ", date=" + date + "]";
	}

}
