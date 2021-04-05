/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.artgallery.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

	public enum ProductStatus {
		Selling, Sold, RestockSoon, DisplayOnly, Suspended
	}
	
	public enum SupportedDelivery {
		Mail, Pickup, Both
	}

	// Product Attributes


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "product_status")
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus productStatus;
	
	@Column(name = "SupportedDelivery")
	@Enumerated(EnumType.ORDINAL)
	private SupportedDelivery supportedDelivery;
	
	@Column(name = "price")
	private int price;
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "picture")
	private String pictureUrl;

	public String getPictureUrl(){
		return pictureUrl;
	}
	
	public void setPictureUrl(String url) {
		this.pictureUrl=url;
	}
	
	// Product Associations
	@ManyToOne(optional = false)
	@JoinColumn(name="Seller_id")
	private Seller seller;
	@OneToOne(mappedBy = "product", optional = true/*, cascade = CascadeType.ALL*/,orphanRemoval=true)
	private Order order;
	@ManyToOne(optional = true)
	@JoinColumn(name = "Promotion_id")
	private Promotion promotion;


	public long getId() {
		return id;
	}

	public void setId(long aId) {
		this.id=aId;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus aProductStatus) {
		
		this.productStatus = aProductStatus;
	}
	
	public SupportedDelivery getSupportedDelivery() {
		
		return this.supportedDelivery;
	}
	
	public void setSupportedDelivery(SupportedDelivery aSupportedDelivery) {
		
		this.supportedDelivery = aSupportedDelivery;
	}

	public int getPrice() {
		return price;
	}

	public boolean setPrice(int aPrice) {
		boolean wasSet = false;
		price = aPrice;
		wasSet = true;
		return wasSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		if(this.seller!=null) return;
		this.seller = seller;
	
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order aOrder) {
		
		this.order = aOrder;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion aPromotion) {
		this.promotion = aPromotion;
	}

}