import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;




public class ECommerceMarket {

	List<Product> products = new ArrayList<Product>();
	List<Supplier> suppliers = new ArrayList<Supplier>();
	Cart cart = new Cart();
	List<Order> orders = new ArrayList<Order>();
	void addProduct(String name, List<String> keywords)
	{
		products.add(new Product(name, keywords));
	}
	void addSupplier(String name, String address)
	{
		suppliers.add(new Supplier(name, address));
	}
	void addSupplierProduct(int productId, int supplierId, int stock, 
			float price, int discountPercent)
	{
		SupplierProduct sp = 
				new SupplierProduct(supplierId, productId, stock, price, discountPercent);
		for(Product p:products)
		{
			if(productId==p.id)
			{
				p.supplierList.add(sp);
			}
		}

	}
	
	List<Product> findProduct(String searchString)
	{
		List<Product> favourableProducts = new ArrayList<Product>();
		for(Product p:products)
		{
			if(p.keywords.contains(searchString))
			{
				favourableProducts.add(p);
			}
		}
		return favourableProducts;
	}
	public void addToCart(int pId, int supplierId, int quantity) 
	{
		boolean productExists=false;
		Product currentProduct=null;
		for(Product p:products)
		{
			if(pId==p.id)
				{
				productExists=true;
				currentProduct=p;
				break;
				}
		}
		if(!productExists)
		{
			return;
		}
		boolean SupplierExists=false;
		int stock = 0;//for the specified supplier of this product;
		SupplierProduct currentSupplierProduct=null;
		for(SupplierProduct sp:	currentProduct.supplierList)
		{
			if(supplierId==sp.supplierID)
			{
				SupplierExists=true;
				stock = sp.stock;
				currentSupplierProduct=sp;
			}
				
		}
		if(!(SupplierExists))
		{
			return;
		}
		
		
		//get current SupplierProduct object
		if(quantity>stock)
			{
			return;
			}
		cart.addItem(pId, supplierId, quantity,currentSupplierProduct.price,
				currentSupplierProduct.priceAfterDiscount());
		System.out.println("item added to cart");
		
	}
	public void placeOrder() {
		if(cart.cartItems.isEmpty())
		{
			System.out.println("No items in cart");
			return;
		}
		Order order = new Order(cart.cartItems,new Date());
		//add order
		orders.add(order);
		//reduce items from supplier record
		for(CartItem ci:cart.cartItems)
		{
			for(Product p:products)
			{
				if(ci.productId==p.id)
				{
					for(SupplierProduct sp:p.supplierList)
					{
						if(ci.supplierId==sp.supplierID)
							sp.stock-=ci.quantity;
					}
				}
			}
		}
		
		//empty cart
		cart.empty();
		System.out.println("Order Placed"+order);
		
	}
	public void viewCartItems() {
		for(CartItem ci:cart.viewItems())
		{
			System.out.println(ci);
		}
	}
	public void viewSuppliers() {
		for(Supplier s:suppliers)
		{
			System.out.println(s);
		}
		
	}
	
	public void viewProduct(int productId)
	{
		for(Product p:products)
		{
			if(productId==p.id)
				System.out.println(p);
		}
		
	}
	
	
	

}
