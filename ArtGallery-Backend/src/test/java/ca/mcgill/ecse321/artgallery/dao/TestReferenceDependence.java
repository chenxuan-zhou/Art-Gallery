package ca.mcgill.ecse321.artgallery.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ca.mcgill.ecse321.artgallery.model.*;
import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;

import java.sql.Date;

@SpringBootTest
public class TestReferenceDependence {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private PromotionRepository promotionRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		orderRepository.deleteAll();
		promotionRepository.deleteAll();
		productRepository.deleteAll();
		customerRepository.deleteAll();
		sellerRepository.deleteAll();
		managerRepository.deleteAll();
	}

	/*@chen
	 * From Product side, we set Promotion. Then See if we can get product from 
	 * Promotion side.
	 */
	@Test
	public void testReferenceProductAndPromotion(){
		// sample seller
		String email = "Gabriel@gmail.com";
		String name = "testSeller";
		String pwd = "123";
		Account.AccountStatus status = Account.AccountStatus.Active;
		String profile = "Introduction";
		double income = 1.0;
		Seller seller = new Seller();
		seller.setStatus(status);
		seller.setName(name);
		seller.setPassword(pwd);
		seller.setEmail(email);
		seller.setProfile(profile);
		seller.setIncome(income);
		sellerRepository.save(seller);

		//sample manager
		String mEmail = "Hubert@gmail.com";
		String mName = "testManager";
		String mPwd = "123";
		Manager testManager = new Manager();
		testManager.setEmail(mEmail);
		testManager.setName(mName);
		testManager.setPassword(mPwd);
//		HashSet<Promotion> promotions = new HashSet<Promotion>();
//		testManager.setPromotion(promotions);
		managerRepository.save(testManager);

		// sample promotion
		String start = "2020-08-01";
		String end="2020-09-01";
		Date start_date =Date.valueOf(start);
		Date end_date =Date.valueOf(end);
		Promotion promt=new Promotion();
		promt.setStartDate(start_date);
		promt.setEndDate(end_date);
		promt.setManager(testManager);
//		ArrayList<Product> toBePromoted = new ArrayList<Product>();
//		promt.setProduct(toBePromoted);
		promotionRepository.save(promt);

		// sample product
		int price = 100;
		String pName = "Drawing";
		Product.ProductStatus pStatus = Product.ProductStatus.Selling;
		Product.SupportedDelivery sd = Product.SupportedDelivery.Pickup;
		Product product = new Product();
		product.setPrice(price);
		product.setName(pName);
		product.setProductStatus(pStatus);
		product.setSupportedDelivery(sd);
		product.setPromotion(promt);
		product.setSeller(seller);

		productRepository.save(product);


		Promotion newpromt=promotionRepository.findPromotionByStartDate(start_date).iterator().next();
		assertEquals(pName,newpromt.getProduct().get(0).getName());
	}

	/*@chen
	 *Set product from Order side, then see if we can get Order from Product.
	 * 
	 * 0 to 1
	 */
	@Test
	public void testReferenceOrderAndProduct(){
		// sample seller
		String email = "Hamilton@gmail.com";
		String name = "testSeller";
		String pwd = "123";
		Account.AccountStatus status = Account.AccountStatus.Active;
		String profile = "Introduction";
		double income = 1.0;
		Seller seller = new Seller();
		seller.setEmail(email);
		seller.setName(name);
		seller.setPassword(pwd);
		seller.setStatus(status);
		seller.setIncome(income);
		seller.setProfile(profile);
		sellerRepository.save(seller);

		//product initialization
		int price = 100;
		String pName = "Drawing";
		Product.ProductStatus pStatus = Product.ProductStatus.Selling;
		Product.SupportedDelivery sd = Product.SupportedDelivery.Pickup;
		Product product = new Product();
		product.setPrice(price);
		product.setName(pName);
		product.setProductStatus(pStatus);
		product.setSupportedDelivery(sd);
		product.setSeller(seller);
		productRepository.save(product);

		// add a dummy customer for the order
		String cEmail = "Iris@gmail.com";
		String cName = "testCustomer";
		String cPwd = "123";
		Account.AccountStatus cStatus = Account.AccountStatus.Active;
		double cBalance = 0.0;
		Customer testCustomer = new Customer();
		testCustomer.setStatus(cStatus);
		testCustomer.setName(cName);
		testCustomer.setPassword(cPwd);
		testCustomer.setEmail(cEmail);
		testCustomer.setBalance(cBalance);
//		ArrayList<Order> myOrder = new ArrayList<Order>();
//		testCustomer.setOrder(myOrder);
		customerRepository.save(testCustomer);

		//order initialization
		String address = "McGill";
		String ordered = "2020-08-01";
		String shipped="2020-09-01";
		int oPrice = 998;
		Date ordered_date =Date.valueOf(ordered);
		Date shipped_date =Date.valueOf(shipped);
		DeliveryMethod dm = DeliveryMethod.Mail;
		OrderStatus oStatus = OrderStatus.New;
		Order order = new Order();
		order.setDeliveryMethod(dm);
		order.setOrderStatus(oStatus);
		order.setAddress(address);
		order.setOrdered(ordered_date);
		order.setShipped(shipped_date);
		order.setPrice(oPrice);
		//set Product from order side, then get order from product side.
		order.setProduct(product);
		order.setCustomer(testCustomer);
		order.setSeller(seller);
		orderRepository.save(order);       
		Product testproduct=productRepository.findByName(pName).iterator().next();
		assertEquals(shipped_date,testproduct.getOrder().getShipped());
	}


	/*@chenxuan-zhou
	 * Set Seller from Order side, then see if we can get Order from Seller.
	 * 
	 * one to many
	 */
	@Test
	public void testReferenceOrderAndSeller(){
		//seller initialization
		String email = "testseller@gmail.com";
		Seller seller = new Seller();
		seller.setEmail(email);

		sellerRepository.save(seller);

		// product initialization 
		// need product or else cannot save order
		int price = 500;
		String name = "Another Drawing";
		Product.ProductStatus status = Product.ProductStatus.Selling;

		Product product = new Product();
		product.setPrice(price);
		product.setName(name);
		product.setProductStatus(status);
		product.setSeller(seller);
		productRepository.save(product);
		
		// add a dummy customer for the order
		String cEmail = "Jenny@gmail.com";
		String cName = "testCustomer";
		String cPwd = "123";
		Account.AccountStatus cStatus = Account.AccountStatus.Active;
		double cBalance = 0.0;
		Customer testCustomer = new Customer();
		testCustomer.setStatus(cStatus);
		testCustomer.setName(cName);
		testCustomer.setPassword(cPwd);
		testCustomer.setEmail(cEmail);
		testCustomer.setBalance(cBalance);
//		ArrayList<Order> myOrder = new ArrayList<Order>();
//		testCustomer.setOrder(myOrder);
		customerRepository.save(testCustomer);

		//order initialization
		String address = "Home";
		Order order = new Order();
		order.setAddress(address);
		order.setProduct(product);
		order.setSeller(seller);
		order.setCustomer(testCustomer);
		orderRepository.save(order);       
		
		Seller testSeller=sellerRepository.findSellerByEmail(email);
		assertEquals(address,testSeller.getOrders().get(0).getAddress());      

	}


	/*@chenxua-zhou
	 * From Order side, we set Customer. 
	 * Then See if we can get Order from Customer side.
	 */
	@Test
	public void testReferenceOrderAndCustomer(){

		// customer initialization
		String email = "imabadcustomer@gmail.com";
		Customer customer = new Customer();
		customer.setEmail(email);

		customerRepository.save(customer);

		//seller initialization
		String sEmail = "Muhammad@gmail.com";
		String sName = "ECSE221TEAM11";
		String pwd = "123";
		Account.AccountStatus status = Account.AccountStatus.Active;
		String profile = "Introduction";
		double income = 1.0;
		Seller seller = new Seller();
		seller.setStatus(status);
		seller.setName(sName);
		seller.setPassword(pwd);
		seller.setProfile(profile);
		seller.setIncome(income);
		seller.setEmail(sEmail);
		sellerRepository.save(seller);
		
		// product initialization 
		// need product or else cannot save order
		int price = 1000000;
		String name = "Starry Night";

		Product product = new Product();
		product.setPrice(price);
		product.setName(name);
		product.setSeller(seller);
		productRepository.save(product);


		//order initialization
		Order order = new Order();
		order.setProduct(product);
		order.setCustomer(customer);
		order.setSeller(seller);

		orderRepository.save(order);    


		Customer gotCustomer = customerRepository.findCustomerByEmail(email);
		assertEquals(seller.getEmail(), gotCustomer.getOrder().get(0).getSeller().getEmail());

	}

	/*@chenxua-zhou
	 * From Product side, we set Seller. 
	 * Then See if we can get Product from Seller side.
	 */
	@Test
	public void testReferenceProductAndSeller(){

		// seller initialization
		String email = "Henry@gmail.com";
		Seller seller = new Seller();
		seller.setEmail(email);

		sellerRepository.save(seller);


		// product initialization 
		// need product or else cannot save order
		int price = 324;
		String name = "Elaborate Math Equations";

		Product product = new Product();
		product.setPrice(price);
		product.setName(name);

		product.setSeller(seller);

		productRepository.save(product);


		Seller gotSeller = sellerRepository.findSellerByEmail(email);
		assertEquals(name, gotSeller.getProducts().get(0).getName());

	}




}
