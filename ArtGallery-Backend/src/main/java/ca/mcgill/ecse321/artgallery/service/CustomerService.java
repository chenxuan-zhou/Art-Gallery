package ca.mcgill.ecse321.artgallery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Transactional
	public Customer createCustomer(String name, String email, String password) {
		if (name == null) {
			throw new IllegalArgumentException("Name not provided");
		}
		if (email == null) {
			throw new IllegalArgumentException("Email not provided");
		}
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Password not provided");
		}
		
		if (customerRepository.findCustomerByEmail(email) != null) {
			throw new IllegalArgumentException("Account with the email already exists.");
		}
		Customer customer = new Customer();
		// Set parameters
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);

		// Default values for a new customer account
		customer.setStatus(AccountStatus.New);
		customer.setBalance(0d);

		customerRepository.save(customer);
		return customer;
	}
	
	@Transactional
	public Customer getCustomer(String email) {
		Customer customer = customerRepository.findCustomerByEmail(email);
		if(customer == null) throw new IllegalArgumentException("Can not find customer with this email");
		return customer; // null if not found
	}

	
	@Transactional
	public void changeName(String email,String name) {
		if(name==null || name.isEmpty()) {
			throw new IllegalArgumentException("Name not provided.");
		}
		Customer customer = customerRepository.findCustomerByEmail(email);
		if(customer==null) {
			throw new IllegalArgumentException("Can not find customer with this email");
		}
		customer.setName(name);
		customerRepository.save(customer);
	}
	
	@Transactional
	public void changePassword(String email, String newPassword, String confirmNewPassword) {
		Customer customer = customerRepository.findCustomerByEmail(email);
		if(customer==null) throw new IllegalArgumentException("Can not find customer with this email");
		if(newPassword == null || newPassword.equals("")) throw new IllegalArgumentException("password cannot be empty");
		if (customer.getStatus()!= AccountStatus.Active) throw new IllegalArgumentException("Must login first");
		if (!newPassword.equals(confirmNewPassword)) throw new IllegalArgumentException("password does not match");
		customer.setPassword(newPassword);
		customerRepository.save(customer);
	}
	
	@Transactional
	public void changeBalance(String email, double amount) {
		Customer customer = customerRepository.findCustomerByEmail(email);		
		if(customer == null) {
			throw new IllegalArgumentException("Can not find customer with this email");
		}
		double origin = customer.getBalance();
		origin += amount;
		customer.setBalance(origin);
		customerRepository.save(customer);
	}
	
	@Transactional
	public void login(String email, String password) {
		Customer testCustomer = customerRepository.findCustomerByEmail(email);

		if (customerRepository.findCustomerByEmail(email) == null) {
			throw new IllegalArgumentException("Login failed, this email does not match any existing customer account.");
		}

		if (!testCustomer.getPassword().equals(password)) {
			throw new IllegalArgumentException("Incorrect password.");
		}

		testCustomer.setStatus(Account.AccountStatus.Active);
		customerRepository.save(testCustomer);
	}
	
	@Transactional
	public void logout(String email) {
		Customer testCustomer = customerRepository.findCustomerByEmail(email);

		if (customerRepository.findCustomerByEmail(email) == null) {
			throw new IllegalArgumentException("Logout failed, this email does not match any existing customer account.");
		}
		if (testCustomer.getStatus()!=AccountStatus.Active) {
			throw new IllegalArgumentException("Must login first");
		}

		testCustomer.setStatus(Account.AccountStatus.Inactive);
		customerRepository.save(testCustomer);
	}
	
	@Transactional
	public List<Customer> getAllCustomers() {
		return toList(customerRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for(T t: iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
