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

import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;

/**
 * @author chenxuan-zhou
 */

@ExtendWith(MockitoExtension.class)
public class TestSellerService {
	@Mock
	private SellerRepository sellerRepository;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private SellerService service;
	 
	
	private static final String SELLER_NAME = "TestSeller";
	private static final String SELLER_EMAIL = "TestSeller@gmail.com";
	private static final String SELLER_PASSWORD = "TestSellerP";
	private static final String SELLER_PASSWORD_WRONG = "TestSellerWrongP";
	private static final String SELLER_PROFILE = "TestSellerProfile";
	private static final long SELLER_INCOME = 1000;
	private static final AccountStatus SELLER_STATUS = AccountStatus.Active;
	

	private static final long PRODUCT_ID = 11;
	private static final long ORDER_ID = 12;
	
	
	private static final String SELLER_NAME2 = "TestSeller2";
	private static final String SELLER_EMAIL2 = "TestSeller2@gmail.com";
	private static final String SELLER_PASSWORD2 = "TestSellerP2";
	private static final String SELLER_PASSWORD_WRONG2 = "TestSellerWrongP2";
	private static final String SELLER_PROFILE2 = "TestSellerProfile2";

	private static final long PRODUCT_ID2 = 21;
	private static final long ORDER_ID2 = 22;
	
	private static final String SELLER_EMAIL_BLOCKED = "TestSellerBlocked@gmail.com";
	
	
	
	@BeforeEach
	public void setMockOutput() {
		
        Seller seller = new Seller();
        seller.setName(SELLER_NAME);
        seller.setEmail(SELLER_EMAIL);
        seller.setPassword(SELLER_PASSWORD);
        seller.setProfile(SELLER_PROFILE);
        seller.setIncome(SELLER_INCOME);
        seller.setStatus(SELLER_STATUS);
        
    	Product product = new Product();
    	product.setId(PRODUCT_ID);
    	seller.addProduct(product);
    	
    	Order order = new Order();
    	order.setId(ORDER_ID);
    	order.setProduct(product);
    	seller.addOrder(order);
    	
    	
    	Seller sellerBlocked = new Seller();
    	sellerBlocked.setEmail(SELLER_EMAIL_BLOCKED);
    	sellerBlocked.setPassword(SELLER_PASSWORD);
    	sellerBlocked.setStatus(AccountStatus.Blocked);
    	
    	
    	// findSellerByEmail
		lenient().when(sellerRepository.findSellerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(SELLER_EMAIL)) {	
	        	return seller;
	        	
	        } else if (invocation.getArgument(0).equals(SELLER_EMAIL_BLOCKED)) {
	        	return sellerBlocked;
	        } else {
	            return null;
	        }
	    });
		
		// save
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(sellerRepository.save(any(Seller.class))).thenAnswer(returnParameterAsAnswer);
		
		// findAll
		lenient().when(sellerRepository.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<Seller> sellers = new ArrayList<Seller>();
			sellers.add(seller);
			return sellers;
		});
		
				
	}
	
	@Test
	public void testCreateSeller() {
		Seller createdSeller = service.createSeller(SELLER_NAME2, SELLER_EMAIL2, SELLER_PASSWORD2, SELLER_PROFILE2);
		// check seller created
		assertNotNull(createdSeller); 
		// check match right input  
		assertEquals(SELLER_NAME2, createdSeller.getName());
		assertEquals(SELLER_EMAIL2, createdSeller.getEmail());
		assertEquals(SELLER_PASSWORD2, createdSeller.getPassword());
		assertEquals(SELLER_PROFILE2, createdSeller.getProfile());
		// check default fields initialized
		assertEquals(AccountStatus.New, createdSeller.getStatus());  
		assertEquals(0d, createdSeller.getIncome());
	}
	  
	@Test
	public void testCreateSellerExisted() {
		Seller createdSeller = null;
		String error = null;
		try {
			createdSeller = service.createSeller(SELLER_NAME, SELLER_EMAIL, SELLER_PASSWORD, SELLER_PROFILE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdSeller);
		assertEquals("Account with the email already exists.",error);

	}
	
	@Test
	public void testCreateSellerNull() {
		Seller createdSeller = null;
		String error = null;
		
		// null/empty email/password mirror each other's behavior
		// so it suffices to test just 2 out of 4 branches
		
		// test null
		try {
			createdSeller = service.createSeller(SELLER_NAME2, null, SELLER_PASSWORD2, SELLER_PROFILE2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdSeller);
		assertEquals("Email not provided.",error);
		
		// test 
		try {
			createdSeller = service.createSeller(SELLER_NAME2, SELLER_EMAIL2, "", SELLER_PROFILE2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(createdSeller);
		assertEquals("Password not provided.",error);
		
		
	}
	
	@Test
	public void testGetSeller() {
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertNotNull(seller);
		assertEquals(SELLER_EMAIL, seller.getEmail());
		assertEquals(SELLER_PASSWORD, seller.getPassword());
		assertEquals(SELLER_STATUS, seller.getStatus());
		
	}
	
	@Test
	public void testChangeName() {
		service.changeName(SELLER_EMAIL, SELLER_NAME2);
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(SELLER_NAME2, seller.getName());
	}
	
	@Test
	public void testChangeNameNull() {
		// not necessary to test email not exist, see testCreateSellerNull

		String error = null;
		try {
			service.changeName(SELLER_EMAIL, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Name not provided.",error);
	}

	@Test
	public void testAddIncome() {
		service.addIncome(SELLER_EMAIL, 100);
		double finalIncome = 100 + SELLER_INCOME;
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(finalIncome,seller.getIncome());
	}
	
	@Test
	public void testChangeProfile() {
		
		service.changeProfile(SELLER_EMAIL, SELLER_PROFILE2);
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(SELLER_PROFILE2,seller.getProfile());
	}
	
	@Test
	public void testGetAllSellers() {
		List<Seller> sellers = service.getAllSellers();
		assertEquals(sellers.size(),1);
		assertEquals(sellers.get(0).getEmail(),SELLER_EMAIL);
	}
	
	@Test
	public void testLogout() {
		service.logout(SELLER_EMAIL);
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(AccountStatus.Inactive,seller.getStatus());
	}
	
	@Test
	public void testLogoutWrongId() {
		String error = null;
		try {
		service.logout("random");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Can not find seller with this email",error);
	}
	
	@Test
	public void testLogoutWrongStatus() {
		String error = null;
		service.logout(SELLER_EMAIL);
		try {
			service.logout(SELLER_EMAIL);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Must login first", error);
	}
	
	@Test
	public void testLogin() {
		service.logout(SELLER_EMAIL);	//make seller logout first
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(AccountStatus.Inactive,seller.getStatus());
		
		service.login(SELLER_EMAIL,SELLER_PASSWORD );
		assertEquals(AccountStatus.Active,seller.getStatus());	
	}
	
	@Test
	public void testLoginWrongPassword() {
		String error=null;
		service.logout(SELLER_EMAIL);	//make seller logout first
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals(AccountStatus.Inactive,seller.getStatus());
		try {
		service.login(SELLER_EMAIL,"wrong" );
		}catch (IllegalArgumentException e) {
			error =e.getMessage();
			
		}
		assertEquals("Wrong password.",error);	
	}
	
	@Test
	public void testLoginWrongId() {
		String error = null;
		try {
		service.login("random","wrong");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Can not find seller with this email",error);
	}
	
	@Test
	public void testLoginBlockedAccount() {
		String error = null;
		try {
		service.login(SELLER_EMAIL_BLOCKED,SELLER_PASSWORD);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Account blocked.",error);
	}
	
	@Test
	public void testChangePassword() {
		service.changePassword(SELLER_EMAIL, "newPassword");
		Seller seller = service.getSeller(SELLER_EMAIL);
		assertEquals("newPassword", seller.getPassword());
	}
	
	@Test
	public void testChangePasswordWrongStatus() {
		String error = null;
		service.logout(SELLER_EMAIL);
		try {
			service.changePassword(SELLER_EMAIL,"ChangePassword");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Must login first", error);
	}
	
	@Test
	public void testChangePasswordWrongId() {
		String error = null;
		try {
			service.changePassword("me@mail","ChangePassword");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Can not find seller with this email", error);
	}
	
	
}
