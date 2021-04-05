package ca.mcgill.ecse321.artgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import antlr.collections.List;
import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.ManagerRepository;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Product.ProductStatus;
import ca.mcgill.ecse321.artgallery.model.Product.SupportedDelivery;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Manager;


@ExtendWith(MockitoExtension.class)
public class TestProductService {
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private SellerRepository sellerRepository;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private PromotionRepository promotionRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private ManagerRepository managerRepository;
	


	
	private static final long PRODUCT_ID = 1;
	private static final long PRODUCT_ID2 = 2;
	private static final long PRODUCT_ID3 = 3;
	private static final long PRODUCT_ID4 = 4;
	private static final String NAME = "drawing";
	private static final ProductStatus PRODUCT_STATUS = ProductStatus.Selling; 
	private static final SupportedDelivery DELIVERY = SupportedDelivery.Both;
	private static final String PICTURE = "www.4399.com";
	private static final int PRICE = 99;
	private static final int PRICE2 = 20;
	
	private static final String SELLER_NAME = "TestSeller";
	private static final String SELLER_EMAIL = "TestSeller@gmail.com";
	private static final String SELLER_EMAIL4 = "TestSeller4@gmail.com";
	private static final String SELLER_PASSWORD = "TestSellerP";
	private static final String SELLER_PROFILE = "TestSellerProfile";
	private static final long SELLER_INCOME = 1000;
	private static final AccountStatus SELLER_STATUS = AccountStatus.Active;
	private static final AccountStatus SELLER_STATUS2 = AccountStatus.Inactive;
	
	private static final long PROMOTION_ID=11;
	
	private static final long ORDER_ID=22;
	
	private static final String CUSTOMER = "customer@gmail.com";
	
	private static final String MANAGER = "manager@gmail.com";
	
	@BeforeEach
	public void setMockOutput() {
		
		// product
		Seller seller = new Seller();
        seller.setName(SELLER_NAME);
        seller.setEmail(SELLER_EMAIL);
        seller.setPassword(SELLER_PASSWORD);
        seller.setProfile(SELLER_PROFILE);
        seller.setIncome(SELLER_INCOME);
        seller.setStatus(SELLER_STATUS);
		
		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setName(NAME);
		product.setPrice(PRICE);
		product.setSupportedDelivery(DELIVERY);
		product.setProductStatus(PRODUCT_STATUS);
		product.setSeller(seller);
		
    	Order order = new Order();
    	order.setId(ORDER_ID);
        order.setProduct(product);
        
        seller.addOrder(order);	 
        seller.addProduct(product);
        
        // product 2
        
        Seller seller2 = new Seller();
        seller2.setName(SELLER_NAME);
        seller2.setEmail(SELLER_EMAIL);
        seller2.setPassword(SELLER_PASSWORD);
        seller2.setProfile(SELLER_PROFILE);
        seller2.setIncome(SELLER_INCOME);
        seller2.setStatus(SELLER_STATUS);
		
        Customer customer2 = new Customer();
        customer2.setEmail(CUSTOMER);
        
        Manager manager2 = new Manager();
        manager2.setEmail(MANAGER);
        
        Promotion promotion2 = new Promotion();
        promotion2.setId(PROMOTION_ID);
        promotion2.setManager(manager2);
        
        Order order2 = new Order();
        order2.setId(ORDER_ID);
        order2.setCustomer(customer2);
        
		Product product2 = new Product();
		product2.setId(PRODUCT_ID2);
		product2.setName(NAME);
		product2.setPrice(PRICE);
		product2.setSupportedDelivery(DELIVERY);
		product2.setProductStatus(PRODUCT_STATUS);
		product2.setSeller(seller2);
		product2.setPromotion(promotion2);
		product2.setOrder(order2);
        
		
		// product 3
		Seller seller3 = new Seller();
        seller3.setName(SELLER_NAME);
        seller3.setEmail(SELLER_EMAIL);
        seller3.setPassword(SELLER_PASSWORD);
        seller3.setProfile(SELLER_PROFILE);
        seller3.setIncome(SELLER_INCOME);
        seller3.setStatus(AccountStatus.Active);
		
        Customer customer3 = new Customer();
        customer3.setEmail(CUSTOMER);
        
        Manager manager3 = new Manager();
        manager3.setEmail(MANAGER);
        
        Promotion promotion3 = new Promotion();
        promotion3.setId(PROMOTION_ID);
        promotion3.setManager(manager3);
        
        Order order3 = new Order();
        order3.setId(ORDER_ID);
        order3.setCustomer(customer3);
        
		Product product3 = new Product();
		product3.setId(PRODUCT_ID3);
		product3.setName(NAME);
		product3.setPrice(PRICE);
		product3.setSupportedDelivery(DELIVERY);
		product3.setProductStatus(PRODUCT_STATUS);
		product3.setSeller(seller3);
		product3.setPromotion(promotion3);
		product3.setOrder(null);
		
		//product4
		Seller seller4 = new Seller();
        seller3.setName(SELLER_NAME);
        seller3.setEmail(SELLER_EMAIL4);
        seller3.setPassword(SELLER_PASSWORD);
        seller3.setProfile(SELLER_PROFILE);
        seller3.setIncome(SELLER_INCOME);
        seller3.setStatus(SELLER_STATUS2);
        
		Product product4 = new Product();
		product3.setId(PRODUCT_ID4);
		product3.setName(NAME);
		product3.setPrice(PRICE);
		product3.setSupportedDelivery(DELIVERY);
		product3.setProductStatus(PRODUCT_STATUS);
		product3.setSeller(seller4);
		product3.setPromotion(promotion3);
		product3.setOrder(null);
		
		/*
		 * mock seller
		 */
		lenient().when(sellerRepository.findSellerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(SELLER_EMAIL)) {
	            return seller;	
	        } else if(invocation.getArgument(0).equals(SELLER_EMAIL4)) {
	            return seller4;	
	        } else {
	            return null;
	        }
	    });
				
		/*
		 * mock product
		 */
		
		
		lenient().when(productRepository.findProductById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PRODUCT_ID)) {
				return product;
			} 
			else if(invocation.getArgument(0).equals(PRODUCT_ID2)) {
				return product2;
				
			} else if(invocation.getArgument(0).equals(PRODUCT_ID3)){
				return product3;
				
			}	else if(invocation.getArgument(0).equals(PRODUCT_ID4)){
				return product3;
				
			}
				else {
				return null; 
			}

		});
		
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(productRepository.save(any(Product.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(sellerRepository.save(any(Seller.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderRepository.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(promotionRepository.save(any(Promotion.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(managerRepository.save(any(Manager.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
	}
	 
	/*
	 * Null seller
	 */
	@Test
	public void testCreateProductNullSeller() {
		assertEquals(0, service.getAllProducts().size());
		Seller seller = null;
		String error = null;
		Product product = null;
		try {
			product = service.createProduct(PRODUCT_STATUS, DELIVERY, NAME, null, PRICE, PICTURE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Seller cannot be null", error);	
	}	
	
	
	@Test
	public void testCreateProductWrongSellerStatus() {
		Seller seller = new Seller();
		seller.setEmail(SELLER_EMAIL);
		seller.setStatus(AccountStatus.Inactive);
		String error = null;
		Product product = null;
		try {
			product = service.createProduct(PRODUCT_STATUS, DELIVERY, NAME, SELLER_EMAIL4, PRICE, PICTURE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Must login first", error);	
	}
	
	@Test
	public void testCreateProduct() {
		assertEquals(0, service.getAllProducts().size());
		Seller seller = new Seller();
		seller.setEmail(SELLER_EMAIL);
		seller.setStatus(AccountStatus.Active);
		sellerRepository.save(seller);
		Product product = null;
		try {
			product = service.createProduct(PRODUCT_STATUS, DELIVERY, NAME, SELLER_EMAIL, PRICE, PICTURE);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(product);
		
	}
	
	@Test
	public void deleteProductNull() {
		long id = 100;
		String error = null; 
		try {
		service.deleteProduct(id);
		} catch (IllegalArgumentException e) {
			 error = e.getMessage();
		}
		assertEquals("No such product to delete", error);
	}
	
	@Test
	public void deleteProduct() {
		assertEquals(0, service.getAllProducts().size());
		Seller seller = sellerRepository.findSellerByEmail(SELLER_EMAIL);
		seller.setStatus(AccountStatus.Active);
		Product product = productRepository.findProductById(PRODUCT_ID);
		
		assertNotNull(productRepository.findProductById(PRODUCT_ID));
		String error = null;
		try {
		service.deleteProduct(PRODUCT_ID);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		} 
		assertNull(error);
		assertEquals(0, service.getAllProducts().size());	
	}
	 
	@Test
	public void deleteProductWithOrder() {
		assertEquals(0, service.getAllProducts().size());
		Seller seller = sellerRepository.findSellerByEmail(SELLER_EMAIL);
		seller.setStatus(AccountStatus.Active);
		Product product = productRepository.findProductById(PRODUCT_ID2);
		String error = null;
		try {
		service.deleteProduct(PRODUCT_ID2);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		} 
		assertEquals("Cannot delete product becasue it is in an Order",error);	
	}
	
	@Test
	public void deleteProductWithPromotion() {
		assertEquals(0, service.getAllProducts().size());
		Product product = productRepository.findProductById(PRODUCT_ID3);
		Seller seller = product.getSeller();
		seller.setStatus(AccountStatus.Active);
		sellerRepository.save(seller);
		assertEquals(SELLER_STATUS,product.getSeller().getStatus());
		String error = null;
		try {
		service.deleteProduct(PRODUCT_ID3);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		} 
		assertEquals("Cannot delete product becasue it is in an Promotion",error);	
	}
	
	
	
	@Test
	public void testGetProduct() {
		Product product = productRepository.findProductById(PRODUCT_ID);
		product=null;
		product = service.getProduct(PRODUCT_ID);
		assertNotNull(product);
		assertEquals(NAME, product.getName());
		assertEquals(PRICE, product.getPrice());
		assertEquals(PRODUCT_ID, product.getId());	
	}
	
	@Test
	public void testGetProductWrongId() {
		long id=100;
		String error = null;
		try {
		Product product = service.getProduct(id);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertEquals("Can not find product with is id",error);
	}
	
	@Test
	public void testChangePriceNoId() {
		String error =null;
		try {
			long id=100;
			service.changePrice(id, PRICE2);
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("No such product to change price",error);		
	}

	@Test
	public void testChangePrice() {
		service.changePrice(PRODUCT_ID, PRICE2);
		Product product = service.getProduct(PRODUCT_ID);
		assertEquals(PRICE2,product.getPrice());			
	}
	
	@Test
	public void testChangePriceWrongSellerStatus() {
		String error = null;
		try {
		service.changePrice(PRODUCT_ID4, PRICE2);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}		
		assertEquals("Must login first",error);			
	}
	
	@Test
	public void testChangeStatusWrongId() {
		String error =null;
		try {
			long id=100;
			service.changeStatus(id, ProductStatus.RestockSoon);
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("No such product to change status",error);		
	
	}
		
	@Test
	public void testChangeStatusWrongSellerStatus() {
		String error = null;
		try {
		service.changeStatus(PRODUCT_ID4, ProductStatus.RestockSoon);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}		
		assertEquals("Must login first",error);		
	}

	@Test
	public void testChangeStatus() {	
		service.changeStatus(PRODUCT_ID, ProductStatus.RestockSoon);
		Product product = service.getProduct(PRODUCT_ID);	
		assertEquals(ProductStatus.RestockSoon,product.getProductStatus());		
	}
	
	@Test
	public void testChangeStatusDueToOrder() {	
		service.changeStatusDueToOrder(PRODUCT_ID2);
		Product product = service.getProduct(PRODUCT_ID2);	
		assertEquals(ProductStatus.Sold,product.getProductStatus());		
	}
	
	@Test
	public void testChangeDelivery() {
		service.changeDelivery(PRODUCT_ID, SupportedDelivery.Mail);
		Product product = service.getProduct(PRODUCT_ID);
		assertEquals(SupportedDelivery.Mail,product.getSupportedDelivery());		
	}
	
	@Test
	public void testChangeDeliveryWrongId() {
		String error =null;
		try {
			long id=100;
			service.changeDelivery(id, SupportedDelivery.Mail);
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("No such product to change delivery",error);				
	}
	
	@Test
	public void testChangeDeliveryWrongSellerStatus() {
		String error = null;
		try {
			service.changeDelivery(PRODUCT_ID4, SupportedDelivery.Mail);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}		
		assertEquals("Must login first",error);				
	}
	
	@Test
	public void testChangeName() {
		service.changeName(PRODUCT_ID, "newName");
		Product product =service.getProduct(PRODUCT_ID);
		assertEquals("newName",product.getName());
	}
	
	@Test
	public void testChangeNameWrongId() {
		String error =null;
		try {
			long id=100;
			service.changeName(id, "newName");
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("No such product to change name",error);	
	}
	
	@Test
	public void testChangeNameWrongSellerStatus() {
		String error =null;
		try {
			service.changeName(PRODUCT_ID4, "newName");
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("Must login first",error);	
	}
	
	@Test
	public void testChangePicture() {
		service.changePicture(PRODUCT_ID, "www.7k7k.com");
		Product product = service.getProduct(PRODUCT_ID);
		assertEquals("www.7k7k.com",product.getPictureUrl());
	}
	
	@Test
	public void testChangePictureWrongId() {
		String error =null;
		try {
			long id=100;
			service.changePicture(id, "www.7k7k.com");
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("No such product to change picture",error);	
	}
	
	@Test
	public void testChangePictureWrongSellerStatus() {
		String error =null;
		try {
			service.changePicture(PRODUCT_ID4, "www.7k7k.com");
			}catch (IllegalArgumentException e) {
				error=e.getMessage();
			}
			assertEquals("Must login first",error);	
	}
	
	@Test
	public void testGetAll() {
		String error = null;
		try{
			service.getAllProducts();	
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(error);
	}
		
}
