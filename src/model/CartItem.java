package model;

public class CartItem {
	String productName;
	String supplierName;
	int quantity;
	float itemPriceAfterDiscount;
	float totalPriceAfterDiscount;
	
	@Override
	public String toString() {
		return "CartItem [productName=" + productName + ", supplierName="
				+ supplierName + ", quantity=" + quantity
				+ ", itemPriceAfterDiscount=" + itemPriceAfterDiscount
				+ ", totalPriceAfterDiscount=" + totalPriceAfterDiscount + "]";
	}



	public CartItem(String productName, String supplierName, int quantity,
			float itemPriceAfterDiscount, float totalPriceAfterDiscount) {
		super();
		this.productName = productName;
		this.supplierName = supplierName;
		this.quantity = quantity;
		this.itemPriceAfterDiscount = itemPriceAfterDiscount;
		this.totalPriceAfterDiscount = totalPriceAfterDiscount;
	}



	

}
