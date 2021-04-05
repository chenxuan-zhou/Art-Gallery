package ca.mcgill.ecse321.artgallery.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order {

	// ------------------------
	// ENUMERATIONS
	// ------------------------

	public enum OrderStatus {
		New, Hold, Shipped, Delivered, Closed
	}

	public enum DeliveryMethod {
		Mail, Pickup
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Order Attributes
	@Column(name = "delivery_method")
	@Enumerated(EnumType.ORDINAL)
	private DeliveryMethod deliveryMethod;
	@Column(name = "order_status")
	@Enumerated(EnumType.ORDINAL)
	private OrderStatus orderStatus;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	@Column(name = "address")
	private String address;
	@Column(name = "order_date")
	private Date ordered;
	@Column(name = "ship_date")
	private Date shipped;
	@Column(name = "price")
	private int price;

	// Order Associations
	@OneToOne(optional=false)
	@JoinColumn(name = "Product_id")
	private Product product;
	@ManyToOne(optional = false)
	@JoinColumn(name = "OrderSeller")
	private Seller seller;
	@ManyToOne(optional = false)
	@JoinColumn(name = "OrderCustomer")
	private Customer customer;

	public long getId() {
		return id;
	}

	public void setId(long aId) {
		this.id = aId;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod aDeliveryMethod) {
		this.deliveryMethod = aDeliveryMethod;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus aOrderStatus) {
		this.orderStatus = aOrderStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String aAddress) {
		this.address = aAddress;
	}

	public Date getOrdered() {
		return ordered;
	}

	public void setOrdered(Date aOrdered) {
		this.ordered = aOrdered;
	}

	public Date getShipped() {
		return shipped;
	}

	public void setShipped(Date aShipped) {
		this.shipped = aShipped;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int aPrice) {
		price = aPrice;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;

	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer aCustomer) {
		if(this.customer!=null) return;
		this.customer = aCustomer;
	}

}
