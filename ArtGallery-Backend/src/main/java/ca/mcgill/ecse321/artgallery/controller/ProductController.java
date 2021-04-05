package ca.mcgill.ecse321.artgallery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallery.dto.OrderDto;
import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.dto.SellerDto;
import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Product.ProductStatus;
import ca.mcgill.ecse321.artgallery.model.Product.SupportedDelivery;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.service.ProductService;
import ca.mcgill.ecse321.artgallery.service.SellerService;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

	
	/*
	 * Unable to test this function because product must have a seller otherwise will have error.
	 * 
	 * 
	 */
	
	@Autowired
	private ProductService service;
	@Autowired
	private SellerService sellerService;
	
	
	/**
	 * No input parameters
	 * Http://localhost:8080/products
	 * @return
	 */
	@GetMapping(value = { "/products", "/products/" })
	public List<ProductDto> getAllProducts() {
		return service.getAllProducts().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/product", "/product/" })
	public ProductDto getProduct(@RequestParam("id") Long id) {
		Product product = service.getProduct(id);
		
		return convertToDto(product);
	}
//
//	@PostMapping(value = { "/product/{name}", "/product/{name}/" })
//	public ProductDto createProduct(@PathVariable("name") String name) throws IllegalArgumentException {
//		Product product = service.createProduct(name);
//		return convertToDto(product);
//	}
	
	
	
	/**
	 * Formal business method of adding a product
	 * @param ps
	 * @param sd
	 * @param name
	 * @param sellerEmail
	 * @param price
	 * @return
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 * status=Selling&delivery=Both&
	 * Test: http://localhost:8080/create-product?name=drawing&seller=some@mail&price=100&picture=
	 */
	
	@PostMapping(value = { "/create-product", "/create-product/" })
	public ProductDto createProduct(
			//@RequestParam("status") ProductStatus ps, 
			//@RequestParam("delivery") SupportedDelivery sd,

			@RequestParam("name") String name, 
			@RequestParam("seller") String sellerEmail, 
			@RequestParam("price") int price,
			@RequestParam("picture") String pictureUrl
			) throws IllegalArgumentException {
		Seller seller = sellerService.getSeller(sellerEmail);
		
		//Product product = service.createProduct(ps, sd, name, seller, price,pictureUrl);
		Product product = service.createProduct(ProductStatus.Selling, SupportedDelivery.Both, name, sellerEmail, price,pictureUrl);
		
		return convertToDto(product);
	}
	
	/**
	 * Tested
	 * @param id
	 */
	@DeleteMapping(value = {"/delete-product","/delete-product"})
	public void deleteProduct(@RequestParam("id") Long id) {
		service.deleteProduct(id);	
	}
	
	/**
	 * Kua
	 * @param email
	 * @param id
	 * @param priceString
	 * @throws IllegalArgumentException
	 * Test: http://localhost:8080/change-product-price?seller=some@mail&product-id=1&new-price=79
	 */
	
	@PutMapping(value = {"/update-product-price","/update-product-price"})
	public void changeProductPrice(
			@RequestParam("seller") String email,
			@RequestParam("product-id") Long id,
			@RequestParam("new-price") int price			
			) throws IllegalArgumentException {	
		service.changePrice(id, price);
	}
	
	/**
	 * Test: http://localhost:8080/update-product-status?seller=some@mail&product-id=2&new-status=Sold
	 * @param email
	 * @param id
	 * @param ps
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/update-product-status","/update-product-status"})
	public void changeProductStatus(
			@RequestParam("seller") String email,
			@RequestParam("product-id") Long id,
			@RequestParam("new-status") String ps			
			) throws IllegalArgumentException {
		
		service.changeStatus(id, convertStatus(ps));
	}
	
	/**
	 * http://localhost:8080/update-product-delivery?seller=some@mail&product-id=2&new-delivery=Mail
	 * @param email
	 * @param id
	 * @param option
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/update-product-delivery","/update-product-delivery"})
	public void changeDelivery(
			@RequestParam("seller") String email,
			@RequestParam("product-id") Long id,
			@RequestParam("new-delivery") String option			
			) throws IllegalArgumentException {
		
		service.changeDelivery(id, convertDelivery(option));
	}

	/**
	 * http://localhost:8080/update-product-name?seller=some@mail&product-id=2&new-name=asdsad
	 * @param email
	 * @param id
	 * @param name
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/update-product-name","/update-product-name"})
	public void changeName(
			@RequestParam("seller") String email,
			@RequestParam("product-id") Long id,
			@RequestParam("new-name") String name			
			) throws IllegalArgumentException {
		service.changeName(id, name);
	} 
	
	/**
	 * http://localhost:8080/update-product-picture?seller=some@mail&product-id=2&new-picture=
	 * @param email
	 * @param id
	 * @param url
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/update-product-picture","/update-product-picture"})
	public void changePicture(
			@RequestParam("seller") String email,
			@RequestParam("product-id") Long id,
			@RequestParam("new-url") String url			
			) throws IllegalArgumentException {
		service.changePicture(id, url);
	}
	
	/**
	 * Convert product to productDto
	 * @param p
	 * @return
	 */
	protected static ProductDto convertToDto(Product p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Product!");
		}
		ProductDto productDto = new ProductDto(p.getName());
		productDto.setId(p.getId());
		productDto.setPrice(p.getPrice());
		productDto.setProductStatus(convertStatus(p.getProductStatus()));
		productDto.setSupportedDelivery(convertOption(p.getSupportedDelivery()));
		productDto.setPictureUrl(p.getPictureUrl());
		
		productDto.setOrder(convertToId(p.getOrder()));
		productDto.setSeller(p.getSeller().getEmail());
		return productDto;
	}
	
	/**
	 * Convert product status to ProductDto status
	 * @param status
	 * @return
	 */
	static ProductDto.ProductStatus convertStatus(Product.ProductStatus status){
		switch (status) {
		case Selling: return ProductDto.ProductStatus.Selling;
		case Sold: return ProductDto.ProductStatus.Sold; 
		case RestockSoon: return ProductDto.ProductStatus.RestockSoon;
		case DisplayOnly: return ProductDto.ProductStatus.DisplayOnly;
		case Suspended: return ProductDto.ProductStatus.Suspended;
		default: throw new IllegalArgumentException("No such product status");
		
		}
		
	}
	
	/**
	 * Convert product status to ProductDto status
	 * @param status
	 * @return
	 */
	static ProductDto.SupportedDelivery convertOption(Product.SupportedDelivery option){
		switch (option) {
		case Mail: return ProductDto.SupportedDelivery.Mail;
		case Pickup: return ProductDto.SupportedDelivery.Pickup;
		case Both: return ProductDto.SupportedDelivery.Both;
		default: throw new IllegalArgumentException("No such delivery option");	
		}
		
	}
	
	/**
	 * Convert product status to ProductDto status
	 * @param status
	 * @return
	 */
	private Product.ProductStatus convertStatus(String status){
		switch (status) {
		case "Selling": return Product.ProductStatus.Selling;
		case "Sold": return Product.ProductStatus.Sold; 
		case "RestockSoon": return Product.ProductStatus.RestockSoon;
		case "DisplayOnly": return Product.ProductStatus.DisplayOnly;
		case "Suspended": return Product.ProductStatus.Suspended;
		default: throw new IllegalArgumentException("No such product status");
		}	
	}
	
	/**
	 * Convert product status to ProductDto status
	 * @param status
	 * @return
	 */
	private Product.SupportedDelivery convertDelivery(String status){
		switch (status) {
		case "Mail": return Product.SupportedDelivery.Mail;
		case "Pickup": return Product.SupportedDelivery.Pickup;
		case "Both": return Product.SupportedDelivery.Both;
		default: throw new IllegalArgumentException("No such delivery option");	
		}	
	}
	
	/** @author chenxuan-zhou
	 *  @param products : a list of Product to convert to ProductDto
	 *  @return : a list of ProductDto corresponding to the Product list
	 */
	protected static List<ProductDto> convertToDto(List<Product> products) {
		if (products == null) {
			return null;
		}
		// if(products.size()==0) return null;
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		for (Product p : products) {
			productsDto.add(convertToDto(p));
		}
		return productsDto;
	}
	
	/**
	 *	A new product does not need order
	 *  Delete throw new IllegalArgumentException("no such order!");
	 */
	private static Long convertToId(Order o) {
		if (o == null) return null;
		return o.getId();
	}
	
	
	
	

}
