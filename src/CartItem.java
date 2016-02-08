
public class CartItem {
	int productId;
	int supplierId;
	int quantity;
	float price;
	float priceAfterDiscount;

	public CartItem(int productId, int supplierId, int quantity, float price,
			float priceAfterDiscount) {
		super();
		this.productId = productId;
		this.supplierId = supplierId;
		this.quantity = quantity;
		this.price = price;
		this.priceAfterDiscount = priceAfterDiscount;
	}

	@Override
	public String toString() {
		return "CartItem [productId=" + productId + ", supplierId="
				+ supplierId + ", quantity=" + quantity + ", price=" + price
				+ ", priceAfterDiscount=" + priceAfterDiscount + "]";
	}

	

}
