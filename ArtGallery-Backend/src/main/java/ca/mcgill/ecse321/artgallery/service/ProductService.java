package ca.mcgill.ecse321.artgallery.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallery.dao.OrderRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.model.Product.ProductStatus;
import ca.mcgill.ecse321.artgallery.model.Product.SupportedDelivery;
import ca.mcgill.ecse321.artgallery.model.Promotion;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SellerRepository sellerRepository; 
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PromotionRepository promotionRepository;
	
//	@Transactional
//	public Product createProduct(String name ) {
//		Product product = new Product();
//		product.setProductStatus(ProductStatus.DisplayOnly);
//		product.setName(name);
//		productRepository.save(product);
//		return product;		
//	}
	 
	@Transactional
	public Product createProduct(ProductStatus ps, SupportedDelivery sd, String name, String sellerEmail, int price, String url) {
		Seller seller = sellerRepository.findSellerByEmail(sellerEmail);
		if (seller==null) throw new IllegalArgumentException("Seller cannot be null");
		if(seller.getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");
		
		Product product = new Product();
		product.setProductStatus(ps);
		product.setSupportedDelivery(sd);
		product.setSeller(seller);
		seller.addProduct(product);
		product.setName(name);
		product.setPrice(price);
		product.setPictureUrl(url);

		
		productRepository.save(product);
		return product;
	}
	
	@Transactional
	public Product getProduct(Long id) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("Can not find product with is id");
		return product;
	}
	

	@Transactional
	public List<Product> getAllProducts() { 
		return toList(productRepository.findAll());
	}
	  
	@Transactional 
	public void deleteProduct(long id){
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to delete");
		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		
		
		if(product.getOrder()!=null) throw new IllegalArgumentException("Cannot delete product becasue it is in an Order");
		if(product.getPromotion()!=null) throw new IllegalArgumentException("Cannot delete product becasue it is in an Promotion");
		productRepository.deleteById(id);			
	}
	
	@Transactional
	public void changePrice(Long id, int price) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change price");

		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		

		
		if(product.getProductStatus()==ProductStatus.Sold || 
				product.getProductStatus()==ProductStatus.Suspended) 
			throw new IllegalArgumentException("Product has been sold or suspended");
		
		product.setPrice(price);
		productRepository.save(product);
	}
	 
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	@Transactional
	public void changeStatus(Long id, ProductStatus ps) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change status");
		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		
		
		if(product.getProductStatus()==ProductStatus.Sold || 
				product.getProductStatus()==ProductStatus.Suspended) 
			throw new IllegalArgumentException("Product has been sold or suspended");
		
		product.setProductStatus(ps);
		productRepository.save(product);
		
	}
	
	@Transactional
	public void changeStatusDueToOrder(Long id) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change status");	
		product.setProductStatus(ProductStatus.Sold);
		productRepository.save(product);	
	}
	
	@Transactional
	public void changeDelivery(Long id, SupportedDelivery option) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change delivery");
		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		
		
		if(product.getProductStatus()==ProductStatus.Sold || 
				product.getProductStatus()==ProductStatus.Suspended) 
			throw new IllegalArgumentException("Product has been sold or suspended");
		
		product.setSupportedDelivery(option);
		productRepository.save(product);
		
	}
	
	@Transactional
	public void changeName(Long id, String name) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change name");

		if(product.getProductStatus()==ProductStatus.Sold || 
				product.getProductStatus()==ProductStatus.Suspended) 
			throw new IllegalArgumentException("Product has been sold or suspended");		
			
		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		

		product.setName(name);	

		productRepository.save(product);
	}
	
	@Transactional
	public void changePicture(Long id, String url) {
		Product product = productRepository.findProductById(id);
		if(product==null) throw new IllegalArgumentException("No such product to change picture");
		if(product.getSeller().getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");		
		
		if(product.getProductStatus()==ProductStatus.Sold || 
				product.getProductStatus()==ProductStatus.Suspended) 
			throw new IllegalArgumentException("Product has been sold or suspended");
		
		product.setPictureUrl(url);
		productRepository.save(product);
	}
	
}
