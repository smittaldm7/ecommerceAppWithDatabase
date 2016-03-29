package model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order {
	
	
	private int id;	
	Timestamp date;
	float totalBillAmount=0;
	List<OrderItem> OrderItems = new ArrayList<OrderItem>();
	
	public Order(int id, Timestamp date, float totalBillAmount) {
		super();
		this.setId(id);
		this.date = date;
		this.totalBillAmount = totalBillAmount;
	}

	@Override
	public String toString() {
		return "Order [id=" + getId() + ", date=" + date + ", totalBillAmount="
				+ totalBillAmount + ", OrderItems=" + OrderItems + "]";
	}
	
	public String view() {
		return "Order [id=" + getId() + ", date=" + date + ", totalBillAmount="
				+ totalBillAmount  + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
