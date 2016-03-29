package model;

public class SupplierProduct {

	String supplierName;
	String productName;
	float price;
	float discountPercent;
	int stock;
	
	public SupplierProduct(String supplierName, String productName, 
				int stock, float price, float discountPercent) 
	{
		super();
		this.supplierName = supplierName;
		this.productName = productName;
		this.stock = stock;
		this.price = price;
		this.discountPercent = discountPercent;
	
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public String getProductName() {
		return productName;
	}
	
	public int getStock() {
		return stock;
	}


	public float getPrice() {
		return price;
	}


	public float getDiscountPercent() {
		return discountPercent;
	}



	float priceAfterDiscount()
	{
		return (price * ((100f-discountPercent)/100f));
	}
	
	@Override
	public String toString() {
		return "SupplierProduct [supplierName=" + supplierName
				+ ", productName=" + productName + ", stock=" + stock
				+ ", price=" + price + ", discountPercent=" + discountPercent
				+ "]";
	}
}
