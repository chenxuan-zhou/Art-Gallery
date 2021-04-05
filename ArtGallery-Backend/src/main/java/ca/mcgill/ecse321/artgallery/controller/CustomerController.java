package ca.mcgill.ecse321.artgallery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallery.dto.CustomerDto;
import ca.mcgill.ecse321.artgallery.dto.SellerDto;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;
import ca.mcgill.ecse321.artgallery.service.CustomerService;
import ca.mcgill.ecse321.artgallery.service.OrderService;
@CrossOrigin(origins = "*")
@RestController
@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	@Autowired
	private OrderService orderService;

	/**
	 * Formal business method for creating a new customer account 
	 * @param name The preferred display name
	 * @param email The PK, as well as account identifier
	 * @param password
	 * @return CustomerDto
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 * 
	 * Test: http://localhost:8080/create-customer?name="BokunZhao"&email="Sam@2565876330@outlook.com"&password="123"
	 * Test passed
	 */
	@PostMapping(value = {"/create-customer", "/create-customer/"})
	public CustomerDto createCustomer(@RequestParam("name") String name, @RequestParam("email")String email,
			@RequestParam("password")String password) throws IllegalArgumentException{
		Customer customer = service.createCustomer(name, email, password);
		return convertToDto(customer);		
	}
	
	@GetMapping(value = {"/customer", "/customer/"})
	public CustomerDto getCustomer(@RequestParam("email") String email,
			@RequestParam(name = "password", required = false) String password) throws IllegalArgumentException {
		Customer customer = service.getCustomer(email);
		// return only basic info if password not provided
		if (password==null || !password.equals(customer.getPassword())) {
			customer.setPassword(null);
			customer.setBalance(-1);
		}
		return convertToDto(customer);
	}
	
	//TODO add methods like SellerController
	
	@PutMapping(value = {"/change-customer-name","/change-customer-name/"})
	public CustomerDto changeCustomerName(
			@RequestParam("customer") String email,
			@RequestParam("new_name") String newName
			) throws IllegalArgumentException {	
		service.changeName(email, newName);
		return convertToDto(service.getCustomer(email));
	}

	@PutMapping(value = {"/change-customer-password","/change-customer-password/"})
	public CustomerDto changeCustomerPassword(
			@RequestParam("customer") String email,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword
			) throws IllegalArgumentException {	
		service.changePassword(email, newPassword, confirmPassword);
		return convertToDto(service.getCustomer(email));
	}
	
	@PutMapping(value = {"/change-customer-balance","/change-customer-balance"})
	public void changeBalance(
			@RequestParam("customer") String email,
			@RequestParam("deltaIncome") double deltaIncome		
			) throws IllegalArgumentException {	
		service.changeBalance(email, deltaIncome);
	}
	
	@PutMapping(value = {"/customer-login","/customer-login/"})
	public CustomerDto login(
			@RequestParam("customer") String email,
			@RequestParam("password") String password			
			) throws IllegalArgumentException {	
		service.login(email, password);
		return convertToDto(service.getCustomer(email));
	}
	
	@PutMapping(value = {"/customer-logout","/customer-logout/"})
	public CustomerDto logout(
			@RequestParam("customer") String email
			) throws IllegalArgumentException {	
		service.logout(email);
		return convertToDto(service.getCustomer(email));
	}
	
	@PutMapping(value = {"/customer-receive", "customer-receive/"})
	public CustomerDto confirmReception(
			@RequestParam("orderID") long ordeID, 
			@RequestParam("customer") String email
			) throws IllegalArgumentException{
		orderService.changeOrderStatus(ordeID, OrderStatus.Delivered);
		return convertToDto(service.getCustomer(email));
	}
	
	@GetMapping(value = {"/customers", "/customers/"})
	public List<CustomerDto> getAllCustomers(){
		return service.getAllCustomers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	static CustomerDto convertToDto(Customer c) {
		CustomerDto converted = new CustomerDto(c.getName(),c.getEmail(), c.getPassword(), c.getBalance(),
				ManagerController.convertStatus(c.getStatus()),
				OrderController.convertToDto(c.getOrder()));
		return converted;
	}

}
