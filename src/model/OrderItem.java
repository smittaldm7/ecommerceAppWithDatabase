package model;

public class OrderItem {
	String productName;
	String supplierName;
	int quantity;
	float itemPriceAfterDiscount;
	
	@Override
	public String toString() {
		return "OrderItem [productName=" + productName + ", supplierName="
				+ supplierName + ", quantity=" + quantity 
				+ ", itemPriceAfterDiscount=" + itemPriceAfterDiscount + "]";
	}

	public OrderItem(String productName, String supplierName, int quantity,
			float itemPriceAfterDiscount) {
		super();
		this.productName = productName;
		this.supplierName = supplierName;
		this.quantity = quantity;
		this.itemPriceAfterDiscount = itemPriceAfterDiscount;
	}

	
	
}
