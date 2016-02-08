import java.util.ArrayList;
import java.util.List;


public class Cart {
	List<CartItem> cartItems = new ArrayList<CartItem>();
	void addItem(int productId, int quantity)
	{
		//Iterate through items to see if item is already in cart
		//if it is increase quantity
		//else add item to cart
		boolean itemExistsInCart=false;
		
		for(CartItem ci:cartItems)
		{
			if (ci.productId==productId)
			{
				itemExistsInCart=true;
				ci.quantity+=quantity;
			}
		}
		if(!itemExistsInCart)
			cartItems.add(new CartItem(productId,quantity));
	}
	
	List<CartItem> viewItems()
	{
		return cartItems;
	}

}
