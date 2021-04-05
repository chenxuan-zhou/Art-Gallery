package ca.mcgill.ecse321.artgallery.dto;


public class ProductDto {

	public ProductDto() {
	}
	
	public ProductDto(String name) {
		this.name=name;
	}
	
	public ProductDto(ProductStatus ps, SupportedDelivery sd, String name, int price, String seller, Long order, Long promotion,String pictureUrl) {
		this.name=name;
		this.productStatus = ps;
		this.supportedDelivery = sd;
		this.price = price;
		this.seller = seller;
		this.order = order;
		this.promotion = promotion;
		this.pictureUrl=pictureUrl;

	}
	
	public enum ProductStatus {
		Selling, Sold, RestockSoon, DisplayOnly, Suspended
	}
	
	public enum SupportedDelivery {
		Mail, Pickup, Both
	}

	private Long id;
	private ProductStatus productStatus;
	private SupportedDelivery supportedDelivery;
	private int price;
	private String name;

	private String seller;
	private Long order;
	private Long promotion;
	private String pictureUrl;
	
	public String getPicutreUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String url) {
		this.pictureUrl=url;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long aId) {
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

	public void setPrice(int aPrice) {
		price = aPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		if(this.seller!=null) return;
		this.seller = seller;
	
	}
	
	public Long getOrder() {
		return order;
	}

	public void setOrder(Long aOrder) {
		
		this.order = aOrder;
	}

	public Long getPromotion() {
		return promotion;
	}

	public void setPromotion(Long aPromotion) {
		this.promotion = aPromotion;
	}


}
