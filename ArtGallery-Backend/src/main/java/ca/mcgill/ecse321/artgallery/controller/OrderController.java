package ca.mcgill.ecse321.artgallery.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallery.dto.OrderDto;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;
import ca.mcgill.ecse321.artgallery.service.CustomerService;
import ca.mcgill.ecse321.artgallery.service.OrderService;
import ca.mcgill.ecse321.artgallery.service.ProductService;
import ca.mcgill.ecse321.artgallery.service.SellerService;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private CustomerService customerService;

	@GetMapping(value = { "/orders", "/orders/" })
	public List<OrderDto> getAllOrders() {
		return orderService.getAllOrders().stream().map(o -> convertToDto(o)).collect(Collectors.toList());
	}
	
	@GetMapping(value = {"/order", "/order/"})
	public OrderDto getOrder(@RequestParam("id") long id) {
		return convertToDto(orderService.getOrder(id));
	}
	
	/**
	 * Formal Business method to create an order, with all parameters specified.
	 * 
	 * @param deliveryMethod
	 * @param address
	 * @param ordered
	 * @param shipped
	 * @param price
	 * @param productId
	 * @param sellerEmail
	 * @param customerEmail
	 * @return OrderDto an Dto of the newly created order
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 * 
	 *  testing sequence:
	 *  1. Create a new seller:
	 *  localhost:8080/create-seller?name=aName&email="leonardo"&password="12332"&profile="ILikeToDraw"
	 *  
	 *  2. Seller login:
	 *  localhost:8080/login?email=leonardo.davinci@italy.com&password=12332
	 *  
	 *  3. Seller uploads a product (that will be sold):
	 *  localhost:8080/create-product?status=Selling&delivery=Both&name=Portrait of Isabella d'Este&seller=leonardo.davinci@italy.com&price=99998&picture=https://www.leonardodavinci.net/images/drawings/portrait-of-isabella-deste.jpg
	 *  
	 *  4. Create a new customer that'll placing the order:
	 *  localhost:8080/create-customer?name=Bokun Zhao&email=Sam2565876330@outlook.com&password=5029374
	 *  
	 *  5. Customer login:
	 *  localhost:8080/customer-login?email=Sam2565876330@outlook.com&password=5029374
	 *  
	 *  6. Places (create) the order with above seller, product and customer:
	 *  localhost:8080/create-order?dm=Mail&address=845 Sherbrooke St W, Montreal, Quebec H3A 0G4&ordered=2020-10-25&shipped=2020-10-28&price=99998&product=1&seller=leonardo.davinci@italy.com&customer=Sam2565876330@outlook.com
	 *  
	 *  At time of order creation, shipped date is an estimate of when the order will be shipped,
	 *  when the status is changed to SHIPPED the shipped date will be updated with actual ship date
	 *  
	 */
	@PostMapping(value = { "/create-order", "/create-order/" })
	public OrderDto createOrder(@RequestParam("dm") DeliveryMethod deliveryMethod,
			@RequestParam("address") String address,
			@RequestParam("ordered")@DateTimeFormat(pattern = "yyyy-mm-dd") String ordered, @RequestParam("shipped")@DateTimeFormat(pattern = "yyyy-mm-dd") String shipped,
			@RequestParam("price") int price, @RequestParam("product") long productId,
			@RequestParam("seller")String sellerEmail, @RequestParam("customer") String customerEmail)
			throws IllegalArgumentException {
		
		Date orderedDate = Date.valueOf(ordered);
		Date shippedDate = Date.valueOf(shipped);
		Product product= productService.getProduct(productId);
		Seller seller = sellerService.getSeller(sellerEmail);
		Customer customer = customerService.getCustomer(customerEmail);
		Order order = orderService.createOrder(deliveryMethod, address, orderedDate, shippedDate, price, product, seller, customer);
		return convertToDto(order);
	}
	
	/**
	 *  Method to change the status of an order.
	 * @param id
	 * @param os
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 */
	@PostMapping(value = { "/change-order-status", "/change-order-status/" })
	public void changeOrderStatus(@RequestParam("id") long id, @RequestParam("new_status") OrderStatus os) throws IllegalArgumentException {
		orderService.changeOrderStatus(id, os);
	}
	
	/**
	 * HTTP://localhost:8080/order-status?id=2&new_status=Shipped
	 * @param id
	 * @param os
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = { "/order-status", "/order-status/" })
	public void changeStatus(@RequestParam("id") long id, @RequestParam("new_status") OrderStatus os) throws IllegalArgumentException {
		orderService.changeOrderStatus(id, os);
	}
	
	/**
	 * HTTP://localhost:8080/shpping-data?id=2&shppined=2020-10-11
	 * @param id
	 * @param shipped
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = { "/shipping-date", "/shipping-date/" })
	public void shippingDate(@RequestParam("id") long id, @RequestParam("shipped")@DateTimeFormat(pattern = "yyyy-mm-dd") String shipped) throws IllegalArgumentException {
		Date shippedDate = Date.valueOf(shipped);
		orderService.changeDate(id, shippedDate);	
	}
	
	
	/**
	 * Method to change the delivery method of an order.
	 * 
	 * @param id
	 * @param dm
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 */
	@PostMapping(value = { "/change-delivery-method", "/change-delivery-method/" })
	public void changeDeliveryMethod(@RequestParam("id") long id, @RequestParam("new_dm") DeliveryMethod dm) throws IllegalArgumentException {
		orderService.changeDeliveryMethod(id, dm);
	}
	
	/**
	 * HTTP://localhost:8080/delivery-method?id=2&new_dm=Both
	 * @param id
	 * @param dm
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = { "/delivery-method", "/delivery-method/" })
	public void changeMethod(@RequestParam("id") long id, @RequestParam("new_dm") DeliveryMethod dm) throws IllegalArgumentException {
		orderService.changeDeliveryMethod(id, dm);
	}
	/**
	 * Method to change the delivery address of an order.
	 * 
	 * @param id
	 * @param address
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 */
	@PostMapping(value = { "/change-address", "/change-address/" })
	public void changeAddress(@RequestParam("id") long id, @RequestParam("new_address") String address) throws IllegalArgumentException {
		orderService.changeAddress(id, address);
	}
	
	
//	// ONLY A TESTING METHOD!
//	@PostMapping(value = { "/test-create-order", "/test-create-order/" })
//	public OrderDto createOrder(@PathVariable("price")int price) {
//		OrderDto order = new OrderDto();
//		order.setPrice(price);
//		return order;
//	}
	
	
	protected static OrderDto convertToDto(Order o) {
		if (o == null) throw new IllegalArgumentException("no such order!");
		OrderDto converted = new OrderDto(o.getDeliveryMethod(), o.getOrderStatus(), o.getAddress(),
				o.getOrdered(), o.getShipped(), o.getPrice(),
				o.getProduct().getId(), o.getSeller().getEmail(), o.getCustomer().getEmail());
				converted.setId(o.getId());
				converted.setPicture(o.getProduct().getPictureUrl());
		return converted;
	}
	
	/** @author chenxuan-zhou
	 *  @param orders : a list of Order to convert to ProductOrder
	 *  @return : a list of OrderDto corresponding to the Order list
	 */
	protected static List<OrderDto> convertToDto(List<Order> orders) {
		if (orders == null) {
			return null;
		}
		List<OrderDto> ordersDto = new ArrayList<OrderDto>();
		for (Order o : orders) {
			ordersDto.add(convertToDto(o));
		}
		return ordersDto;
	}
	
}
