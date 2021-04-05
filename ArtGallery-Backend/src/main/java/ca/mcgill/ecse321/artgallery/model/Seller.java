package ca.mcgill.ecse321.artgallery.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "seller")
public class Seller extends Account {

	// Seller Attributes
	@Column(name = "profile")
	private String profile;
	@Column(name = "income")
	private double income;

	// Seller Associations
	@OneToMany(mappedBy="seller", fetch = FetchType.EAGER)
	private List<Product> products;
	@OneToMany(mappedBy="seller", fetch = FetchType.EAGER)
	private List<Order> orders;
	
	public Seller() {
		this.products = new ArrayList<Product>();
		this.orders = new ArrayList<Order>();
	}

	public String getProfile() {
		return profile;
	}

	public boolean setProfile(String aProfile) {
		boolean wasSet = false;
		profile = aProfile;
		wasSet = true;
		return wasSet;
	}

	public double getIncome() {
		return income;
	}

	public boolean setIncome(double aIncome) {
		boolean wasSet = false;
		income = aIncome;
		wasSet = true;
		return wasSet;
	}

	public Product getProduct(int index) {
		Product aProduct = products.get(index);
		return aProduct;
	}
	
	public List<Product> getProducts() {
		return this.products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Order> getOrders() {
		return this.orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void addProduct(Product product) {
		if (product != null) {
			this.products.add(product);
		}
		
	}

	public void addOrder(Order order) {
		if (order != null) {
			this.orders.add(order);
		}
	}

}