package ca.mcgill.ecse321.artgallery.dto;

import java.util.List;

public class CustomerDto extends AccountDto {


	private double balance;
	private List<OrderDto> orders;

	public CustomerDto(String name, String email, String password, double balance, AccountStatus ac, List<OrderDto> orders)
	{
		this.name=name;
		this.email=email;
		this.password=password;
		this.balance=balance;
		this.status=ac;
		this.orders = orders;

	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}

	public List<OrderDto> getOrder() {
		return this.orders;
	}

	public void setOrder(List<OrderDto> order) {
		this.orders = order;
	}

}
