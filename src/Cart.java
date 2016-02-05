import java.util.ArrayList;
import java.util.List;


public class Cart {
	List<CartItem> cartItems = new ArrayList<CartItem>();
	void addItem(int productId, int quantity)
	{
		cartItems.add(new CartItem(productId,quantity));
	}
	
	List<CartItem> viewItems()
	{
		return cartItems;
	}

}
