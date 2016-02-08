
public class SupplierProduct {
	int supplierID;
	int stock;
	float price;
	int discountPercent;
	
	
	public SupplierProduct(int supplierID, int productID, int stock, float price,
			int discountPercent) {
		super();
		this.supplierID = supplierID;
		this.stock = stock;
		this.price = price;
		this.discountPercent = discountPercent;
		
	}
	
	float priceAfterDiscount()
	{
		return (price * ((100f-discountPercent)/100f));
	}
	
	@Override
	public String toString() {
		return "SupplierProduct [supplierID=" + supplierID + ", stock=" + stock + ", price=" + price
				+ ", discountPercent=" + discountPercent + "]";
	}
}
