package ca.mcgill.ecse321.artgallery.service;

import static org.mockito.Mockito.lenient;

import java.sql.Date;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.ManagerRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Customer;

@ExtendWith(MockitoExtension.class)
public class TestManagerService {

	@InjectMocks
	private ManagerService managerService;

	@Mock
	private PromotionRepository promotionRepository;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private SellerRepository sellerRepository;
	@Mock
	private CustomerRepository customerRepository;

	// a promotion object
	private static final long PROMOID = 123;
	private static final String START = "2020-08-01";
	private static final String END="2020-09-01";
	private static final  Date  PROMOTSTART= Date.valueOf(START);
	private static final  Date  PROMOTEND= Date.valueOf(END);

	// a promotion object 
	private static final long promotId2=234;
	private static final String start2 = "2020-010-01";
	private static final String end2="2020-011-01";
	private static final  Date  promotStart2= Date.valueOf(START);
	private static final  Date  promotEnd2= Date.valueOf(END);

	// a manager A
	private static final String managerEmail="test1@manager.com";
	private static final String managerName="ManagerA";
	private static final String managerPassword="123";
	private static final Account.AccountStatus managerStatus= Account.AccountStatus.Active;

	// a manger B
	private static final String managerBEmail="test2@manager.com";
	private static final String managerBName="ManagerB";
	private static final String managerBPassword="1234";
	private static final AccountStatus MANAGER_STATUS = AccountStatus.New;

	// a product (suspended use)
	private static final  Product.ProductStatus  SELLINGSTATUS= Product.ProductStatus.Selling;
	//    private static final int price=1;
	//    private static final String name="ProductA";
	private static final long PRODUCT_ID=123;


	// a seller (suspended use)
	private static final String SELLER_NAME = "badSeller";
	private static final String SELLER_EMAIL = "badSeller@gmail.com";
	private static final String SELLER_PASSWORD = "badSellerP";
	private static final String SELLER_PROFILE = "violate rules";
	private static final long SELLER_INCOME = 1000;
	private static final AccountStatus SELLER_STATUS = AccountStatus.Active;

	// a customer (suspension use)
	private static final String CUSTOMER_NAME = "badCUSTOMER";
	private static final String CUSTOMER_EMAIL = "badSeller@gmail.com";
	private static final String CUSTOMER_PASSWORD = "badSellerP";
	
	// test use variables
	private static final String NEW_NAME = "new name";
	private static final String NEW_PASSWORD = "1234";

	@BeforeEach
	public void setMockoutput() {
		Manager manager = new Manager();
		manager.setEmail(managerEmail);
		manager.setStatus(managerStatus);
		manager.setName(managerName);
		manager.setPassword(managerPassword);

		Seller seller = new Seller();
		seller.setName(SELLER_NAME);
		seller.setEmail(SELLER_EMAIL);
		seller.setPassword(SELLER_PASSWORD);
		seller.setProfile(SELLER_PROFILE);
		seller.setIncome(SELLER_INCOME);
		seller.setStatus(SELLER_STATUS);

		Customer customer = new Customer();

		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setProductStatus(SELLINGSTATUS);
		seller.addProduct(product);

		Promotion promotion = new Promotion();
		promotion.setId(promotId2);

		lenient().when(sellerRepository.findSellerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(SELLER_EMAIL)) {	
				return seller;

			} else {
				return null;
			}
		});

		lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {	
				return customer;

			} else {
				return null;
			}
		});

		lenient().when(managerRepository.findManagerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(managerEmail)) {	
				return manager;

			} else {
				return null;
			}
		});

		lenient().when(productRepository.findProductById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PRODUCT_ID)) {	
				return product;

			} else {
				return null;
			}
		});

		lenient().when(promotionRepository.findPromotionById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(promotId2)) {	
				return promotion;

			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(sellerRepository.save(any(Seller.class))).thenAnswer(returnParameterAsAnswer);

		lenient().when(managerRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<Manager> managers = new ArrayList<Manager>();
			managers.add(manager);
			return managers;
		});
	}

	@Test
	public void createManangerTest() {
		Manager testManager = managerService.createManager(managerBEmail, managerBName, managerBPassword, MANAGER_STATUS);

		assertNotNull(testManager);
		assertEquals(testManager.getEmail(), managerBEmail);
		assertEquals(testManager.getName(), managerBName);
		assertEquals(testManager.getPassword(), managerBPassword);
		assertEquals(testManager.getStatus(), MANAGER_STATUS);
	}

	@Test
	public void createManagerAlreadyExistsTest() {
		Manager createdManager = null;
		String error = null;
		try {
			createdManager = managerService.createManager(managerEmail, managerName, managerPassword, managerStatus);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdManager);
		assertEquals("Account already exists.", error);

	}

	@Test
	public void findManagerTest() {
		Manager testManager = managerService.findManager(managerEmail);

		assertNotNull(testManager);
		assertEquals(testManager.getEmail(), managerEmail);
		assertEquals(testManager.getName(), managerName);
		assertEquals(testManager.getPassword(), managerPassword);
		assertEquals(testManager.getStatus(), managerStatus);
	}

	@Test
	public void getAllManagerTest() {
		List<Manager> managers = managerService.getAllManagers();

		assertEquals(managers.size(), 1);
		assertEquals(managers.get(0).getEmail(), managerEmail);
	}

	@Test
	public void loginTest() {
		managerService.login(managerEmail, managerPassword);
		assertEquals(managerService.findManager(managerEmail).getStatus(), AccountStatus.Active);
	}

	@Test
	public void loginFailTest() {
		try {
			managerService.login(managerEmail, managerBPassword);
		} catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Incorrect password.");
			assertEquals(managerService.findManager(managerEmail).getStatus(), AccountStatus.Active);
		}
	}

	@Test
	public void logoutTest() {
		managerService.logout(managerEmail);
		assertEquals(managerService.findManager(managerEmail).getStatus(), AccountStatus.Inactive);	
	}
	
	@Test
	public void changeNameTest() {
		boolean isChanged = managerService.changeName(managerEmail, NEW_NAME);
		assertEquals(isChanged, true);
		assertEquals(managerService.findManager(managerEmail).getName(), NEW_NAME);
	}
	
	@Test
	public void changePasswordTest() {
		boolean isChanged = managerService.changePassword(managerEmail, managerPassword, NEW_PASSWORD);
		assertEquals(isChanged, true);
		assertEquals(managerService.findManager(managerEmail).getPassword(), NEW_PASSWORD);
	}
	
	@Test
	public void changePasswordFailTest() {
		try {
			managerService.changePassword(managerEmail, managerBPassword, NEW_PASSWORD);
		}catch(IllegalArgumentException e) {
			assertEquals(managerService.findManager(managerEmail).getPassword(), managerPassword);
			assertEquals(e.getMessage(), "Invalid password, fail to reset password");
		}
	}
	
	@Test
	public void deleteManagerTest() {
		boolean isDeleted = managerService.deleteManager(managerEmail, managerPassword);
		assertEquals(isDeleted, true);
	}
	
	@Test
	public void deleteManagerFailTest() {
		try {
			managerService.deleteManager(managerEmail, managerBPassword);
		}catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Incorrect password. Fail to delete.");
		}
	}

	@Test
	public void suspendProductTest() {
		boolean isSuspended = managerService.suspendProduct(managerEmail, PRODUCT_ID);
		assertEquals(isSuspended, true);
	}

	@Test
	public void suspendAccountSellerTest() {
		boolean isSuspended = managerService.suspendAccount(managerEmail, SELLER_EMAIL);
		assertEquals(isSuspended, true);
	}
	
	@Test
	public void suspendAccountCustomerTest() {
		boolean isSuspended = managerService.suspendAccount(managerEmail, CUSTOMER_EMAIL);
		assertEquals(isSuspended, true);
	}

}
