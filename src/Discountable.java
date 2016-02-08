
public interface Discountable {
	int discountPercent=0;
	float priceAfterDiscount=0;
	float getPriceAfterDiscount();
	
	/*	public float getPriceAfterDiscount()
	{
		return (price*((100-discountPercent)/100));
	}
	*/

}
