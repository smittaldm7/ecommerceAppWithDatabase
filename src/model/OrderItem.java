package model;

import java.sql.Timestamp;

public class OrderItem {
	String productName;
	String supplierName;
	int quantity;
	Timestamp date;
	float itemPriceAfterDiscount;
	float totalPriceAfterDiscount;
	
	@Override
	public String toString() {
		return "OrderItem [productName=" + productName + ", supplierName="
				+ supplierName + ", quantity=" + quantity + ", date=" + date
				+ ", itemPriceAfterDiscount=" + itemPriceAfterDiscount
				+ ", totalPriceAfterDiscount=" + totalPriceAfterDiscount + "]";
	}

	public OrderItem(String productName, String supplierName, int quantity,
			Timestamp date, float itemPriceAfterDiscount,
			float totalPriceAfterDiscount) {
		super();
		this.productName = productName;
		this.supplierName = supplierName;
		this.quantity = quantity;
		this.date = date;
		this.itemPriceAfterDiscount = itemPriceAfterDiscount;
		this.totalPriceAfterDiscount = totalPriceAfterDiscount;
	}

	
	
}
