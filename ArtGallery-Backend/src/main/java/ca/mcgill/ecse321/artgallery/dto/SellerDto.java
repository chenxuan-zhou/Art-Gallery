package ca.mcgill.ecse321.artgallery.dto;

import java.util.List;

public class SellerDto extends AccountDto {
	private String profile;
	private double income;
	private List<ProductDto> products;
	private List<OrderDto> orders;
	
	
	public SellerDto() {		
	}
	
	public SellerDto(String email) {
		this.email=email;
	}
	
	public SellerDto(
			String name, String password, String email, String profile, double income, 
			AccountStatus as, 
			List<ProductDto> products, 
			List<OrderDto> orders) {
		super(name, password, email);
		this.profile = profile;
		this.income = income;
		this.status = as;
		this.products = products;
		this.orders = orders;
	}
	
	
	
	public String getProfile() {
		return profile;
	}
	
	public double getIncome() {
		return income;
	}
	
	public List<ProductDto> getProducts() {
		return this.products;
	}

	public List<OrderDto> getOrders() {
		return orders;
	}


	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public void setIncome(double income) {
		this.income = income;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}
	
	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}


}
