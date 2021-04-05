package ca.mcgill.ecse321.artgallery.service;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Customer;

/**
 * @author chenxuan-zhou
 */

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService service;
	 
	
	private static final String CUSTOMER_NAME = "Bokun Zhao";
	private static final String CUSTOMER_EMAIL = "Sam2565876330@outlook.com";
	private static final String CUSTOMER_PASSWORD = "ECSE321";
	private static final String CUSTOMER_PASSWORD_WRONG = "ECSE211";
	private static final long CUSTOMER_BALANCE = 1000;
	private static final AccountStatus CUSTOMER_STATUS = AccountStatus.Active;
	
	
	private static final String CUSTOMER_NAME2 = "Chenxuan Zhou";
	private static final String CUSTOMER_EMAIL2 = "TracyZhou@gmail.com";
	private static final String CUSTOMER_PASSWORD2 = "6034234";
	private static final long CUSTOMER_BALANCE2 = 200;
	private static final String CUSTOMER_PASSWORD_WRONG2 = "3329344";
	private static final AccountStatus CUSTOMER_STATUS2 = AccountStatus.New;
	
	private static final String CUSTOMER_NAME3 = "Newbie";
	private static final String CUSTOMER_EMAIL3 = "NewComer@gmail.com";
	private static final String CUSTOMER_PASSWORD3 = "randomcharacters";
	
	
	
	@BeforeEach
	public void setMockOutput() {
		
        Customer customer = new Customer();
        customer.setName(CUSTOMER_NAME);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setBalance(CUSTOMER_BALANCE);
        customer.setStatus(CUSTOMER_STATUS);
        
		
        Customer customer2 = new Customer();
        customer2.setName(CUSTOMER_NAME2);
        customer2.setEmail(CUSTOMER_EMAIL2);
        customer2.setPassword(CUSTOMER_PASSWORD2);
        customer2.setBalance(CUSTOMER_BALANCE2);
        customer2.setStatus(CUSTOMER_STATUS2);
    	
    	// findCustomerByEmail
		lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {	
	        	return customer;
	        	
	        } else if (invocation.getArgument(0).equals(CUSTOMER_EMAIL2)) {
	        	return customer2;
	        } else {
	            return null;
	        }
	    });
		
		// save
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		
		// findAll
		lenient().when(customerRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<Customer> customers = new ArrayList<Customer>();
			customers.add(customer);
			return customers;
		});
		
				
	}
	
	@Test
	public void testCreateCustomer() {
		Customer createdCustomer = service.createCustomer(CUSTOMER_NAME3, CUSTOMER_EMAIL3, CUSTOMER_PASSWORD3);
		// check customer created
		assertNotNull(createdCustomer); 
		// check match right input  
		assertEquals(CUSTOMER_NAME3, createdCustomer.getName());
		assertEquals(CUSTOMER_EMAIL3, createdCustomer.getEmail());
		assertEquals(CUSTOMER_PASSWORD3, createdCustomer.getPassword());
		// check default fields initialized
		assertEquals(AccountStatus.New, createdCustomer.getStatus());  
		assertEquals(0d, createdCustomer.getBalance());
	}
	  
	@Test
	public void testCreateCustomerExisted() {
		Customer createdCustomer = null;
		String error = null;
		try {
			createdCustomer = service.createCustomer(CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdCustomer);
		assertEquals("Account with the email already exists.",error);

	}
	
	@Test
	public void testCreateCustomerNull() {
		Customer createdCustomer = null;
		String error = null;
		
		// null/empty email/password mirror each other's behavior
		// so it suffices to test just 2 out of 4 branches
		
		// test null
		try {
			createdCustomer = service.createCustomer(CUSTOMER_NAME2, null, CUSTOMER_PASSWORD2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdCustomer);
		assertEquals("Email not provided",error);
		
		// test 
		try {
			createdCustomer = service.createCustomer(CUSTOMER_NAME, CUSTOMER_EMAIL, "");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdCustomer);
		assertEquals("Password not provided",error);
		
		
	}
	
	@Test
	public void testGetCustomer() {
		Customer seller = service.getCustomer(CUSTOMER_EMAIL);
		assertNotNull(seller);
		assertEquals(CUSTOMER_EMAIL, seller.getEmail());
		assertEquals(CUSTOMER_PASSWORD, seller.getPassword());
		assertEquals(CUSTOMER_STATUS, seller.getStatus());
		
	}
	
	@Test
	public void testChangeName() {
		service.changeName(CUSTOMER_EMAIL, CUSTOMER_NAME2);
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals(CUSTOMER_NAME2, customer.getName());
	}
	
	@Test
	public void testChangeNameNull() {
		// not necessary to test email not exist, see testCreateCustomerNull

		String error = null;
		try {
			service.changeName(CUSTOMER_EMAIL, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Name not provided.",error);
	}

	@Test
	public void testAddIncome() {
		service.changeBalance(CUSTOMER_EMAIL, 100);
		double finalBalance = 100 + CUSTOMER_BALANCE;
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals(finalBalance, customer.getBalance());
	}
	
	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = service.getAllCustomers();
		assertEquals(customers.size(),1);
		assertEquals(customers.get(0).getEmail(),CUSTOMER_EMAIL);
	}
	
	@Test
	public void testLogout() {
		service.logout(CUSTOMER_EMAIL);
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals(AccountStatus.Inactive, customer.getStatus());
	}
	
	@Test
	public void testLogoutWrongId() {
		String error = null;
		try {
		service.logout("random");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Logout failed, this email does not match any existing customer account.", error);
	}
	
	@Test
	public void testLogoutWrongStatus() {
		String error = null;
		service.logout(CUSTOMER_EMAIL);
		try {
			service.logout(CUSTOMER_EMAIL);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Must login first", error);
	}
	
	@Test
	public void testLogin() {
		service.logout(CUSTOMER_EMAIL);	//make customer logout first
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals(AccountStatus.Inactive, customer.getStatus());
		
		service.login(CUSTOMER_EMAIL, CUSTOMER_PASSWORD);
		assertEquals(AccountStatus.Active,customer.getStatus());	
	}
	
	@Test
	public void testLoginWrongPassword() {
		String error=null;
		service.logout(CUSTOMER_EMAIL);	//make customer logout first
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals(AccountStatus.Inactive, customer.getStatus());
		try {
		service.login(CUSTOMER_EMAIL, "wrong" );
		}catch (IllegalArgumentException e) {
			error =e.getMessage();
			
		}
		assertEquals("Incorrect password.", error);	
	}
	
	@Test
	public void testLoginWrongId() {
		String error = null;
		try {
		service.login("random","wrong");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Login failed, this email does not match any existing customer account.",error);
	}
	
	@Test
	public void testChangePassword() {
		service.changePassword(CUSTOMER_EMAIL, "newPassword", "newPassword");
		Customer customer = service.getCustomer(CUSTOMER_EMAIL);
		assertEquals("newPassword", customer.getPassword());
	}
	
	@Test
	public void testChangePasswordWrongStatus() {
		String error = null;
		service.logout(CUSTOMER_EMAIL);
		try {
			service.changePassword(CUSTOMER_EMAIL,"ChangePassword", "ChangePassword");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Must login first", error);
	}
	
	@Test
	public void testChangePasswordWrongId() {
		String error = null;
		try {
			service.changePassword("me@mail","ChangePassword", "ChangePassword");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Can not find customer with this email", error);
	}
	
	
}
