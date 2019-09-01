package model;

public class OrderItem {
	
	int id;
	int supplier_product_id;
	int quantity;
	float itemPriceAfterDiscount;
	float totalPriceAfterDiscount;
	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", supplier_product_id=" + supplier_product_id + ", quantity=" + quantity
				+ ", itemPriceAfterDiscount=" + itemPriceAfterDiscount + ", totalPriceAfterDiscount="
				+ totalPriceAfterDiscount + "]";
	}
	public OrderItem(int supplier_product_id, int quantity, float itemPriceAfterDiscount,
			float totalPriceAfterDiscount) {
		super();
		this.supplier_product_id = supplier_product_id;
		this.quantity = quantity;
		this.itemPriceAfterDiscount = itemPriceAfterDiscount;
		this.totalPriceAfterDiscount = totalPriceAfterDiscount;
	}
	
	
	



	
	
}
