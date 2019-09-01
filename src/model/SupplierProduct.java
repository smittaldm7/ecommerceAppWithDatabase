package model;

public class SupplierProduct {

	int id;
	int supplierId;
	int productId;
	float price;
	float discountPercent;
	int stock;
	

	public SupplierProduct(int supplierId, int productId, float price, float discountPercent, int stock) {
		super();
		this.supplierId = supplierId;
		this.productId = productId;
		this.price = price;
		this.discountPercent = discountPercent;
		this.stock = stock;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
	float priceAfterDiscount()
	{
		return (price * ((100f-discountPercent)/100f));
	}
	
	

	@Override
	public String toString() {
		return "SupplierProduct [id=" + id + ", supplierId=" + supplierId + ", productId=" + productId + ", price="
				+ price + ", discountPercent=" + discountPercent + ", stock=" + stock + "]";
	}
}
