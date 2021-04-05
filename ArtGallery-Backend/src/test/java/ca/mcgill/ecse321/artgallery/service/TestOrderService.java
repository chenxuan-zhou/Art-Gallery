package ca.mcgill.ecse321.artgallery.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import ca.mcgill.ecse321.artgallery.model.Product.ProductStatus;
import ca.mcgill.ecse321.artgallery.model.Product.SupportedDelivery;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;


@ExtendWith(MockitoExtension.class)
public class TestOrderService {
	@Mock
	private OrderRepository orderDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private SellerRepository sellerDao;
	@Mock
	private ProductRepository productDao;

	@InjectMocks
	private OrderService orderService;

	
	// Prepare fields for an Order
	private static final DeliveryMethod dmA = DeliveryMethod.Mail;
	private static final OrderStatus osA = OrderStatus.New;
	private static final long orderIdA = 1;
	private static final String addressA = "845 Sherbrooke St W, Montreal, Quebec H3A 0G4";
	private static final Date orderDateA = Date.valueOf("2020-10-25");
	private static final Date shippedDateA = Date.valueOf("2020-10-28");
	private static final int priceA = 87500;
	
	// Prepare fields for an Order 2
	private static final DeliveryMethod dmB = DeliveryMethod.Pickup;
	private static final OrderStatus osB = OrderStatus.New;
	private static final long orderIdB = 2;
	private static final String addressB = "1455 Boulevard de Maisonneuve O, Montréal, QC H3G 1M8";
	private static final Date orderDateB = Date.valueOf("2020-10-26");
	private static final Date shippedDateB = Date.valueOf("2020-10-31");
	private static final int priceB = 721000;
	
	// Prepare fields for a Customer
	private static final String customerName = "Bokun";
	private static final String customerEmail = "Sam2565876330@outllok.com";
	private static final String customerPassword = "7riwrd31";
	private static int customerBalance = 500;
	private static final AccountStatus customerStatus = AccountStatus.Active;
	
	// Prepare fields for a Seller
	private static final String sellerName = "Leonardo da Vinci";
	private static final String sellerEmail = "ledonardo.vinci@mail.com";
	private static final String sellerPassword = "dipingoleuova";
	private static int sellerIncome = 340;
	private static final AccountStatus sellerStatus = AccountStatus.Active;
	
	// Prepare fields for a Product
	private static final ProductStatus ps = ProductStatus.Selling;
	private static final SupportedDelivery sd = SupportedDelivery.Both;
	private static final long productId = 1;
	private static final int productPrice = 870000;
	private static final String productName = "Sala delle Asse";
	private static final String productPicUrl = "https://upload.wikimedia.org/wikipedia/commons/9/99/Leonardo_Sala_delle_Asse_detail.jpg";
	
	// Prepare fields for a Product 2
	private static final ProductStatus ps2 = ProductStatus.Selling;
	private static final SupportedDelivery sd2 = SupportedDelivery.Both;
	private static final long productId2 = 2;
	private static final int productPrice2 = 720500;
	private static final String productName2 = "Portrait of Isabella d'Este";
	private static final String productPicUrl2 = "https://www.leonardodavinci.net/images/drawings/portrait-of-isabella-deste.jpg";
	
	@BeforeEach
	public void setMockOutput() {
		Seller seller = new Seller();
		seller.setName(sellerName);
		seller.setEmail(sellerEmail);
		seller.setPassword(sellerPassword);
		seller.setIncome(sellerIncome);
		seller.setStatus(sellerStatus);
		
		Product product = new Product();
		product.setId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		product.setProductStatus(ps);
		product.setSupportedDelivery(sd);
		product.setPictureUrl(productPicUrl);
		product.setSeller(seller);
		seller.addProduct(product); // takes care of both ends of the relation?
		
		Product product2 = new Product();
		product2.setId(productId2);
		product2.setName(productName2);
		product2.setPrice(productPrice2);
		product2.setProductStatus(ps2);
		product2.setSupportedDelivery(sd2);
		product2.setPictureUrl(productPicUrl2);
		product2.setSeller(seller);
		seller.addProduct(product2); // takes care of both ends of the relation?
		
		Customer customer = new Customer();
		customer.setName(customerName);
		customer.setEmail(customerEmail);
		customer.setPassword(customerPassword);
		customer.setBalance(customerBalance);
		customer.setStatus(customerStatus);
		
    	Order orderA = new Order();
    	orderA.setId(orderIdA);
        orderA.setSeller(seller);
        orderA.setProduct(product);
        orderA.setPrice(priceA);
        orderA.setCustomer(customer);
        orderA.setAddress(addressA);
        orderA.setOrderStatus(osA);
        orderA.setDeliveryMethod(dmA);
        orderA.setOrdered(orderDateA);
        orderA.setShipped(shippedDateA);
		
    	Order orderB = new Order();
        orderB.setId(orderIdB);
        orderB.setSeller(seller);
        orderB.setProduct(product2);
        orderB.setPrice(priceB);
        orderB.setCustomer(customer);
        orderB.setAddress(addressB);
        orderB.setOrderStatus(osB);
        orderB.setDeliveryMethod(dmB);
        orderB.setOrdered(orderDateB);
        orderB.setShipped(shippedDateB);
		
	    lenient().when(sellerDao.findSellerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(sellerEmail)) {
	        	return seller;
	        } else {
	            return null;
	        }
	    });
	    
	    lenient().when(productDao.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(productId)) {
	        	return product;
	        } else if (invocation.getArgument(0).equals(productId2)){
	        	return product2;
	        } else {
	            return null;
	        }
	    });
	    
	    lenient().when(customerDao.findCustomerByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(sellerEmail)) {
	        	return customer;
	        } else {
	            return null;
	        }
	    });
		
	    lenient().when(orderDao.findById(anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(orderIdA)) {
	            return orderA;
	        } else if (invocation.getArgument(0).equals(orderIdB)){
	            return orderB;
	        } else {
	            return null;
	        }
	    });
	    
	    lenient().when(orderDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {	        
            List<Order> orders = new ArrayList<>();
            orders.add(orderA);
            orders.add(orderB);
            return orders;
	    });
	    
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(productDao.save(any(Product.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(sellerDao.save(any(Seller.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderDao.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void createOrderNoProductTest() {
		//a valid seller
		Seller seller = new Seller();
		seller.setName(sellerName);
		seller.setEmail(sellerEmail);
		seller.setPassword(sellerPassword);
		seller.setIncome(sellerIncome);
		seller.setStatus(sellerStatus);
		
		//a valid customer
		Customer customer = new Customer();
		customer.setName(customerName);
		customer.setEmail(customerEmail);
		customer.setPassword(customerPassword);
		customer.setBalance(customerBalance);
		customer.setStatus(customerStatus);
		
		//no product!
		
		String error = null;
		Order orderWithoutProduct = null;
		
        try {
        	orderWithoutProduct = orderService.createOrder(dmA, addressA, orderDateA, shippedDateA, priceA, null, seller, customer);
        }
        catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        
        assertNull(orderWithoutProduct);
        assertEquals("Order must have one product", error);
	}
	
	@Test
	public void createOrderNoSellerTest() {
		//no seller!
		
		//a valid product
		Product product = new Product();
		product.setId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		product.setProductStatus(ps);
		product.setSupportedDelivery(sd);
		product.setPictureUrl(productPicUrl);
//		product.setSeller(seller);
//		seller.addProduct(product);
		
		//a valid customer
		Customer customer = new Customer();
		customer.setName(customerName);
		customer.setEmail(customerEmail);
		customer.setPassword(customerPassword);
		customer.setBalance(customerBalance);
		customer.setStatus(customerStatus);
		
		String error = null;
		Order orderWithoutSeller = null;
		
        try {
        	orderWithoutSeller = orderService.createOrder(dmA, addressA, orderDateA, shippedDateA, priceA, product, null, customer);
        }
        catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        
        assertNull(orderWithoutSeller);
        assertEquals("Order must have one seller", error);
	}
	
	@Test
	public void createOrderNoCustomerTest() {
		//a valid seller
		Seller seller = new Seller();
		seller.setName(sellerName);
		seller.setEmail(sellerEmail);
		seller.setPassword(sellerPassword);
		seller.setIncome(sellerIncome);
		seller.setStatus(sellerStatus);
		
		//a valid product
		Product product = new Product();
		product.setId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		product.setProductStatus(ps);
		product.setSupportedDelivery(sd);
		product.setPictureUrl(productPicUrl);
		product.setSeller(seller);
		seller.addProduct(product);
		
		// no customer!
		
		String error = null;
		Order orderWithoutCustomer = null;
		
        try {
        	orderWithoutCustomer = orderService.createOrder(dmA, addressA, orderDateA, shippedDateA, priceA, product, seller, null);
        }
        catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        
        assertNull(orderWithoutCustomer);
        assertEquals("Order must have one customer", error);
	}
	
	@Test
	public void createOrder() {
		//a valid seller
		Seller seller = new Seller();
		seller.setName(sellerName);
		seller.setEmail(sellerEmail);
		seller.setPassword(sellerPassword);
		seller.setIncome(sellerIncome);
		seller.setStatus(sellerStatus);
		
		//a valid product
		Product product = new Product();
		product.setId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		product.setProductStatus(ps);
		product.setSupportedDelivery(sd);
		product.setPictureUrl(productPicUrl);
		product.setSeller(seller);
		seller.addProduct(product);
		
		//a valid customer
		Customer customer = new Customer();
		customer.setName(customerName);
		customer.setEmail(customerEmail);
		customer.setPassword(customerPassword);
		customer.setBalance(customerBalance);
		customer.setStatus(customerStatus);
		
		String error = null;
		Order order = null;
		
        try {
        	order = orderService.createOrder(dmA, addressA, orderDateA, shippedDateA, priceA, product, seller, customer);
        }
        catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        
        assertEquals(null, error);
        assertNotNull(order);
        assertEquals(dmA, order.getDeliveryMethod());
        assertEquals(addressA, order.getAddress());
        assertEquals(orderDateA, order.getOrdered());
        assertEquals(shippedDateA, order.getShipped());
        assertEquals(priceA, order.getPrice());
        assertEquals(product, order.getProduct());
        assertEquals(seller, order.getSeller());
        assertEquals(customer, order.getCustomer());

	}
	
    @Test
    public void testGetOrderById(){
        Order order = orderDao.findById(orderIdA);
        assertEquals(sellerEmail,order.getSeller().getEmail());
        assertEquals(productId,order.getProduct().getId());
        assertEquals(customerEmail,order.getCustomer().getEmail());
    }
    
    @Test
    public void testGetAllOrders(){
        List<Order> orders =orderService.getAllOrders();
        assertEquals(orderIdA,orders.get(0).getId());
        assertEquals(orderIdB,orders.get(1).getId());
    }
    
    @Test
    public void testChangeOrderStatusNoOrder() {
    	// 0 as input, no order with id = 0;
    	String error = null;
    	try {
    	orderService.changeOrderStatus(0, OrderStatus.Delivered);
    	} catch(IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("No such order to change status", error);
    	assertEquals(osA, orderDao.findById(orderIdA).getOrderStatus());
    	assertEquals(osB, orderDao.findById(orderIdB).getOrderStatus());
    }
    
    @Test
    public void testChangeOrderStatus() {
    	assertEquals(osA, orderDao.findById(orderIdA).getOrderStatus());
    	orderService.changeOrderStatus(orderIdA, OrderStatus.Shipped);
    	Order order = orderDao.findById(orderIdA);
    	assertEquals(OrderStatus.Shipped, order.getOrderStatus());
    }
    
    @Test
    public void testChangeDeliveryMethodNoOrder() {
    	// 0 as input, no order with id = 0;
    	String error = null;
    	try {
    	orderService.changeDeliveryMethod(0, DeliveryMethod.Mail);
    	} catch(IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("No such order to change delivery method", error);
    	assertEquals(dmA, orderDao.findById(orderIdA).getDeliveryMethod());
    	assertEquals(dmB, orderDao.findById(orderIdB).getDeliveryMethod());
    }
    
    @Test
    public void testChangeDeliveryMethodShipped() {
    	String error = null;
    	Order shippedOrderA = orderDao.findById(orderIdA);
    	
    	//manually change the order status to induce below exception
    	shippedOrderA.setOrderStatus(OrderStatus.Shipped);

    	try {
    	orderService.changeDeliveryMethod(orderIdA, DeliveryMethod.Pickup);
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	assertEquals("Cannot change delivery method, product may already be shipped", error);
    	assertEquals(dmA, orderDao.findById(orderIdA).getDeliveryMethod());
    }
    
    @Test
    public void testChangeDeliveryMethod() {
    	// change from mail to pickup while the order hasn't shipped
    	assertEquals(dmA, orderDao.findById(orderIdA).getDeliveryMethod());
    	orderService.changeDeliveryMethod(orderIdA, DeliveryMethod.Pickup);
    	Order order = orderDao.findById(orderIdA);
    	assertEquals(DeliveryMethod.Pickup, order.getDeliveryMethod());
    }
    
    @Test
    public void testChangeAddressNoOrder() {
    	// 0 as input, no order with id = 0;
    	String error = null;
    	try {
    	orderService.changeAddress(0, "Somewhere over the rainbow, way up high");
    	} catch(IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	
    	assertEquals("No such order to change address", error);
    	assertEquals(addressA, orderDao.findById(orderIdA).getAddress());
    	assertEquals(addressB, orderDao.findById(orderIdB).getAddress());
    }
    
    @Test
    public void testChangeAddressShipped() {
    	String error = null;
    	Order shippedOrderA = orderDao.findById(orderIdA);
    	
    	//manually change the order status to induce below exception
    	shippedOrderA.setOrderStatus(OrderStatus.Shipped);

    	try {
    	orderService.changeAddress(orderIdA, "4101 Sherbrooke St E, Montreal, Quebec H1X 2B2");
    	} catch (IllegalArgumentException e) {
    		error = e.getMessage();
    	}
    	assertEquals("Cannot change address, product may already be shipped", error);
    	assertEquals(addressA, orderDao.findById(orderIdA).getAddress());
    }
    
    @Test
    public void testChangeAddress() {
    	// change address while the order hasn't shipped
    	assertEquals(addressA, orderDao.findById(orderIdA).getAddress());
    	orderService.changeAddress(orderIdA, "3510 Avenue Lionel-Groulx, Montréal, QC H4C 1M7");
    	Order order = orderDao.findById(orderIdA);
    	assertEquals("3510 Avenue Lionel-Groulx, Montréal, QC H4C 1M7", order.getAddress());
    }

}
