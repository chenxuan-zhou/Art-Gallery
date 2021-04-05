package ca.mcgill.ecse321.artgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Customer;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Product.ProductStatus;
import ca.mcgill.ecse321.artgallery.model.Seller;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public Order createOrder(DeliveryMethod deliveryMethod,
			String address, Date ordered, Date shipped, int price,
			Product product, Seller seller, Customer customer) {
		if(product == null){
			throw new IllegalArgumentException("Order must have one product");
		}
		
		if(product.getProductStatus()!=ProductStatus.Selling){
			throw new IllegalArgumentException("Product is not in Selling status");
		}
		
		if(address==null || address.length()==0){
			throw new IllegalArgumentException("Invalide address");
		}
		
		if(seller == null){
			throw new IllegalArgumentException("Order must have one seller");
		}
		if(customer == null){
			throw new IllegalArgumentException("Order must have one customer");
		}
		Order order = new Order();
		order.setDeliveryMethod(deliveryMethod);
		order.setAddress(address);
		order.setOrdered(ordered);
		order.setShipped(shipped);
		order.setPrice(price);
		order.setProduct(product);
		order.setSeller(seller);
		order.setCustomer(customer);
		
		product.setOrder(order);
		product.setProductStatus(ProductStatus.Sold);
		seller.addOrder(order);
		customer.addOrder(order);
		product.setProductStatus(ProductStatus.Sold);
		
		//default values for new orders
		order.setOrderStatus(OrderStatus.New);
		orderRepository.save(order);
		return order;
	}

	@Transactional
	public Order getOrder(long id) {
		Order order = orderRepository.findById(id);
		return order;
	}

	@Transactional
	public List<Order> getAllOrders() {
		return toList(orderRepository.findAll());
	}

	@Transactional
	public void changeOrderStatus(long id, OrderStatus os) {
		Order order = orderRepository.findById(id);
		if(order == null)
			throw new IllegalArgumentException("No such order to change status");
		order.setOrderStatus(os);
		if (os == OrderStatus.Shipped) {
			order.setShipped(new Date(System.currentTimeMillis()));
		}
		else if (os == OrderStatus.Delivered) { // Order fulfilled, all sales are final
			order.getCustomer().setBalance(order.getCustomer().getBalance() - order.getPrice());
			order.getSeller().setIncome(order.getSeller().getIncome() + order.getPrice() * 0.98); // 2 percent revenue goes to our team.
		}
		orderRepository.save(order);
	}

	@Transactional
	public void changeDeliveryMethod(long id, DeliveryMethod dm) {
		Order order = orderRepository.findById(id);
		if(order == null)
			throw new IllegalArgumentException("No such order to change delivery method");
		if(order.getOrderStatus() != OrderStatus.New)
			throw new IllegalArgumentException("Cannot change delivery method, product may already be shipped");
		order.setDeliveryMethod(dm);
		orderRepository.save(order);
	}

	@Transactional
	public void changeDate(long id,Date shipped) {
		Order order = orderRepository.findById(id);
		if(order == null)
			throw new IllegalArgumentException("No such order to change delivery method");
		order.setShipped(shipped);
		order.setOrderStatus(OrderStatus.Shipped);
	}
	
	@Transactional
	public void changeAddress(long id, String addressNew) {
		Order order = orderRepository.findById(id);
		if(order == null)
			throw new IllegalArgumentException("No such order to change address");
		if(order.getOrderStatus() != OrderStatus.New)
			throw new IllegalArgumentException("Cannot change address, product may already be shipped");
		order.setAddress(addressNew);
		orderRepository.save(order);
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for(T t: iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
