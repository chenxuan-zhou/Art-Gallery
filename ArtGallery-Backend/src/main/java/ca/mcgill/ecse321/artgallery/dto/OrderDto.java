package ca.mcgill.ecse321.artgallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.artgallery.model.Order.DeliveryMethod;
import ca.mcgill.ecse321.artgallery.model.Order.OrderStatus;

public class OrderDto {
	
	private DeliveryMethod deliveryMethod;
	private OrderStatus orderStatus;
	// No need for primary key here
	private Long id;
	private String address;
	private Date ordered;
	private Date shipped;
	private int price;
	
	private String picture;
	private Long product;
	private String seller;
	private String customer;
	
	public OrderDto() {
		
	}
	
	public OrderDto(DeliveryMethod deliveryMethod, OrderStatus orderStatus,
			String address, Date ordered, Date shipped, int price,
			Long product, String seller, String customer) {
		this.deliveryMethod = deliveryMethod;
		this.orderStatus = orderStatus;
		this.address = address;
		this.ordered = ordered;
		this.shipped = shipped;
		this.price = price;
		this.product = product;
		this.seller = seller;
		this.customer = customer;
		this.setPicture("picture");
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

	
	public void setId(Long id) {
		this.id=id;
	}
	
	public long getId() {
		return this.id;
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

	public Long getProduct() {
		return this.product;
	}

	public void setProduct(Long product) {
		this.product = product;

	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String aCustomer) {
		if(this.customer!=null) return;
		this.customer = aCustomer;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	
}
