
package ca.mcgill.ecse321.artgallery.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer extends Account {


	// Customer Attributes
	@Column(name = "balance")
	private double balance;

	public Customer() {
		this.order = new ArrayList<Order>();
	}
	
	
	// Customer Associations
	@OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
	private List<Order> order;

	public double getBalance() {
		return balance;
	}

	public boolean setBalance(double aBalance) {
		boolean wasSet = false;
		balance = aBalance;
		wasSet = true;
		return wasSet;
	}

	public List<Order> getOrder() {
		return this.order;
	}
	
	public boolean addOrder(Order aOrder) {
		if (order == null) {
			return false;
		}
		this.order.add(aOrder);
		return true;
	}
	
	public boolean setOrder(List<Order> order) {
		if (order == null) {
			return false;
		}
		this.order = order;
		return true;
	}
	
}