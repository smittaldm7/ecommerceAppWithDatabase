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
			float priceofOrderItem=0;
			//Get prices for each product in the order form eCommerceMarket object amazon
			//multiply it with the corresponding quantity of the object in the order
			//add all those to get the total bill amount
			for(Product p:App.amazon.products)
				{
				if(oi.productId==p.id)
					{
					priceofOrderItem=p.getPrice()*oi.quantity;
					break;
					}
				}
			totalBillAmount+=priceofOrderItem;	
			
		}
	}
	@Override
	public String toString() {
		return "Order [OrderItems=" + OrderItems + ", totalBillAmount="
				+ totalBillAmount + ", date=" + date + "]";
	}

}
