package model;


public class Product {
	int id;
	String name;

	@Override
	public String toString() {
		return "Product [ name=" + name + "]";
	}

	public Product(String name) {
		super();
		this.name = name;

	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

}
