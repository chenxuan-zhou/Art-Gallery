package ca.mcgill.ecse321.artgallery.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.artgallery.model.*;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;
import ca.mcgill.ecse321.artgallery.model.Product.SupportedDelivery;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@SpringBootTest
//@ExtendWith(SpringExension.class)
public class TestArtGalleryPersistence {

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
    	// deletion order is important here due to added foreign key constrants (optional=false)
        orderRepository.deleteAll();
        promotionRepository.deleteAll();
        productRepository.deleteAll();
        customerRepository.deleteAll();
        sellerRepository.deleteAll();
        managerRepository.deleteAll();
        
    }
    
    /*
     * Create test for Customer
     * by Lide 
     */
    @Test
    public void testPersistAndLoadCustomer() {
    	/* Initialize a customer */
    	String email = "Adam@gmail.com";
    	String name = "testCustomer";
    	String pwd = "123";
    	Account.AccountStatus status = Account.AccountStatus.Active;
    	double balance = 0.0;
    	
    	Customer testCustomer = new Customer();
    	testCustomer.setEmail(email);
    	testCustomer.setName(name);
    	testCustomer.setPassword(pwd);
    	testCustomer.setStatus(status);
    	testCustomer.setBalance(balance);
    	
    	/* test steps */
    	customerRepository.save(testCustomer);
    	testCustomer = null;
    	testCustomer = customerRepository.findCustomerByEmail(email); //id is defined as email
    	
    	/* result steps */
    	assertNotNull(testCustomer);
    	assertEquals(email,testCustomer.getEmail());
    	assertEquals(name,testCustomer.getName());
        assertEquals(pwd,testCustomer.getPassword());
        assertEquals(status,testCustomer.getStatus());
        assertEquals(balance,testCustomer.getBalance());
    }
    
    /*
     * Create test for Seller
     * by Lide
     */
    @Test
    public void testPersistAndLoadSeller() {
    	/* Initialize a seller */
    	String email = "Brian@gmail.com";
    	String name = "testSeller";
    	String pwd = "123";
    	Account.AccountStatus status = Account.AccountStatus.Active;
    	String profile = "Introduction";
    	double income = 0.0;
    	
    	Seller testSeller = new Seller();
    	testSeller.setEmail(email);
    	testSeller.setName(name);
    	testSeller.setPassword(pwd);
    	testSeller.setStatus(status);
    	testSeller.setIncome(income);
    	testSeller.setProfile(profile);
    	
    	/* test steps */
    	sellerRepository.save(testSeller);
    	testSeller = null;
    	testSeller = sellerRepository.findSellerByEmail(email); //id is defined as email
    	
    	/* result steps */
    	assertNotNull(testSeller);
    	assertEquals(email,testSeller.getEmail());
    	assertEquals(name,testSeller.getName());
        assertEquals(pwd,testSeller.getPassword());
        assertEquals(status,testSeller.getStatus());
        assertEquals(profile,testSeller.getProfile());
        assertEquals(income,testSeller.getIncome());
    }
    
    /*
     * Create test for Manager
     * By Lide
     */
    @Test
    public void testPersistAndLoadManager() {
    	/* Initialize a manager */
    	String email = "Custard@gmail.com";
    	String name = "testManager";
    	String pwd = "123";
    	Account.AccountStatus status = Account.AccountStatus.Active;
    	
    	Manager testManager = new Manager();
    	testManager.setEmail(email);
    	testManager.setName(name);
    	testManager.setPassword(pwd);
    	testManager.setStatus(status);  	
    	
    	/* test promotion and manager composition */
    	//no need to manually assign a PK as save() automatically assigns a generated PK
		String start = "2020-08-01";
		String end="2020-09-01";
		Date start_date =Date.valueOf(start);
		Date end_date =Date.valueOf(end);

		Promotion promt=new Promotion();
		promt.setStartDate(start_date);
		promt.setEndDate(end_date);
//		ArrayList<Product> dummyPromtList = new ArrayList<Product>();
//		promt.setProduct(dummyPromtList);

		//promotionRepository.save(promt);
		
		Set<Promotion> promts = new HashSet<>();
		testManager.setPromotion(promts);
    	
    	/* test steps */
    	managerRepository.save(testManager);
    	testManager = null;
    	testManager = managerRepository.findManagerByEmail(email); //id is (manually)defined as email
    	
    	
    	/* result steps */
    	assertNotNull(testManager);
    	assertEquals(email,testManager.getEmail());
    	assertEquals(name,testManager.getName());
        assertEquals(pwd,testManager.getPassword());
        assertEquals(status,testManager.getStatus());
        //test cascade on promotion
        assertEquals(promts, testManager.getPromotion());
    }
    
    /*
     * Create test for Product
     * By Kua
     */
    @Test
    public void testPersistAndLoadProduct() {
		// create a dummy seller
		String sEmail = "Denny@gmail.com";
		String sName = "testSeller";
		String sPwd = "123";
		Account.AccountStatus sellerStatus = Account.AccountStatus.Active;
		String sProfile = "Introduction";
		double sIncome = 1.0;
		Seller testSeller = new Seller();
		testSeller.setEmail(sEmail);
		testSeller.setName(sName);
		testSeller.setPassword(sPwd);
		testSeller.setStatus(sellerStatus);
		testSeller.setIncome(sIncome);
		testSeller.setProfile(sProfile);
//		ArrayList<Product> listOfProducts = new ArrayList<Product>();
//		testSeller.setProducts(listOfProducts);
		sellerRepository.save(testSeller);
		
    	// product to be tested
    	int price = 100;
    	String name = "Drawing";
    	Product.ProductStatus status = Product.ProductStatus.Selling;
    	Product product = new Product();
    	product.setPrice(price);
    	product.setName(name);
    	product.setProductStatus(status);
    	product.setSeller(testSeller);
		productRepository.save(product);
		
    	product = null;
    	product = productRepository.findByName(name).iterator().next();
    	
    	assertNotNull(product);
    	assertEquals(name,product.getName());
    	assertEquals(status,product.getProductStatus());
    	//test linkage
    	assertEquals(testSeller.getEmail(), product.getSeller().getEmail());
    }
    /*
     * Create test for load promotion
     * Yutian Fu
     */
    
	@Test
	public void testPersistAndLoadPromotion(){
		//dummy manager
		Manager dummyManager = new Manager();
		dummyManager.setStatus(AccountStatus.New);
		dummyManager.setName("DUM");
		dummyManager.setPassword("qwerty");
		dummyManager.setEmail("Dummy@dumdom.com");
		managerRepository.save(dummyManager);
		
		//promotion to be tested
		String start = "2020-08-01";
		String end="2020-09-01";
		Date start_date =Date.valueOf(start);
		Date end_date =Date.valueOf(end);
		Promotion promt=new Promotion();
		promt.setStartDate(start_date);
		promt.setEndDate(end_date);
		promt.setManager(dummyManager);

		promotionRepository.save(promt); 
		promt=null;
		//promt=promotionRepository.findPromotionById(expected);
		promt = promotionRepository.findPromotionByStartDate(start_date).iterator().next();;
		assertNotNull(promt);
		assertEquals(start_date,promt.getStartDate());
		assertEquals(end_date,promt.getEndDate());
		
		assertEquals(dummyManager.getEmail(), promt.getManager().getEmail());

	}
	/* Create Test for load Order
	 * Yutian Fu
	 */
	@Test
	public void testPersistAndLoadOrder(){
		// add a dummy seller for the order
		String sEmail = "Emily@gmail.com";
		String sName = "testSeller";
		String sPwd = "123";
		Account.AccountStatus sellerStatus = Account.AccountStatus.Active;
		String sProfile = "Introduction";
		double sIncome = 1.0;
		Seller testSeller = new Seller();
		testSeller.setEmail(sEmail);
		testSeller.setName(sName);
		testSeller.setPassword(sPwd);
		testSeller.setStatus(sellerStatus);
		testSeller.setIncome(sIncome);
		testSeller.setProfile(sProfile);
		sellerRepository.save(testSeller);
		
		// add a dummy customer for the order
		String cEmail = "Fred@gmail.com";
		String cName = "testCustomer";
		String cPwd = "123";
		Account.AccountStatus cStatus = Account.AccountStatus.Active;
		double cBalance = 0.0;
		Customer testCustomer = new Customer();
		testCustomer.setEmail(cEmail);
		testCustomer.setName(cName);
		testCustomer.setPassword(cPwd);
		testCustomer.setStatus(cStatus);
		testCustomer.setBalance(cBalance);
		customerRepository.save(testCustomer);
		
		// create a product which dummy seller is selling
    	int p = 100;
    	String name = "Drawing";
    	Product.ProductStatus s = Product.ProductStatus.Selling;
    	Product.SupportedDelivery d = SupportedDelivery.Both;

    	Product product = new Product();
    	product.setPrice(p);
    	product.setName(name);
    	product.setProductStatus(s);
    	product.setSupportedDelivery(d);
    	product.setSeller(testSeller);
    	productRepository.save(product);
		
		//order to be tested
		String address = "McGill";
		String ordered = "2020-08-01";
		String shipped="2020-09-01";
		int price = 998;
		Date ordered_date =Date.valueOf(ordered);
		Date shipped_date =Date.valueOf(shipped);
		DeliveryMethod dm = DeliveryMethod.Mail;
		OrderStatus status = OrderStatus.New;
		Order testOrder=new Order();
		testOrder.setAddress(address);
		testOrder.setDeliveryMethod(dm);
		testOrder.setOrderStatus(status);
		testOrder.setPrice(price);
		testOrder.setShipped(shipped_date);
		testOrder.setOrdered(ordered_date);
		testOrder.setSeller(testSeller);
		testOrder.setCustomer(testCustomer);
    	testOrder.setProduct(product);
    	
		orderRepository.save(testOrder);
		
		testOrder=null;
		testOrder=orderRepository.findByAddress(address).iterator().next();
		

		assertNotNull(testOrder);
		assertEquals(address,testOrder.getAddress());
		assertEquals(shipped_date, testOrder.getShipped());
		assertEquals(testSeller.getEmail(), testOrder.getSeller().getEmail());
		assertEquals(testCustomer.getEmail(), testOrder.getCustomer().getEmail());
		assertEquals(product.getName(), testOrder.getProduct().getName());

	}
    
    

}
