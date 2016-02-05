
public class CartItem {
	int productId;
	int quantity;
	@Override
	public String toString() {
		return "CartItem [productId=" + productId + ", quantity=" + quantity
				+ "]";
	}
	public CartItem(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	

}
