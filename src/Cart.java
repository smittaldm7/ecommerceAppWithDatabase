import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Cart {
	List<CartItem> cartItems = new ArrayList<CartItem>();
	float amount;
	void addItem(int productId, int supplierId, int quantity, float price, 
			float priceAfterDiscount)
	{
		//Iterate through items to see if item is already in cart
		//if it is increase quantity
		//else add item to cart
		boolean itemExistsInCart=false;
		
		for(CartItem ci:cartItems)
		{
			if (ci.productId==productId)
			{
				if(ci.supplierId==supplierId)
					{
					itemExistsInCart=true;
					ci.quantity+=quantity;
					ci.price+=price*quantity;
					ci.priceAfterDiscount+=priceAfterDiscount*quantity;
					}
				
			}
		}
		if(!itemExistsInCart)
			{
			cartItems.add(new CartItem(productId, supplierId, quantity, price*quantity,
					priceAfterDiscount*quantity));
			}
	}
	
	List<CartItem> viewItems()
	{
		return cartItems;
	}
	void empty()
	{
		cartItems=new ArrayList<CartItem>();
	}

	@Override
	public String toString() {
		return "Cart [cartItems=" + cartItems + ", amount=" + amount + "]";
	}
	

}
