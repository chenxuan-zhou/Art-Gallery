package ca.mcgill.ecse321.artgallery.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallery.dto.ProductDto;
import ca.mcgill.ecse321.artgallery.dto.SellerDto;
import ca.mcgill.ecse321.artgallery.model.Seller;
import ca.mcgill.ecse321.artgallery.service.SellerService;

@CrossOrigin(origins = "*")
@RestController
@Controller
public class SellerController {
	@Autowired
	private SellerService service;
	
	
	@GetMapping(value = {"/sellers", "/sellers/"})
	public List<SellerDto> getAllSellers(){
		return service.getAllSellers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
		
	}
	
	/**
	 * @author chenxuan-zhou
	 * @return If password not provided, SellerDto password will be null. 
	 *         Otherwise SellDto contains full information of seller.
	 */
	@GetMapping(value = {"/seller"})
	public SellerDto getSeller(
			@RequestParam(value="email")String email,
			@RequestParam(value="password", required=false) String password
			) throws IllegalArgumentException {
		Seller seller = service.getSeller(email);
		if (password==null || !password.equals(seller.getPassword())) {
			seller.setPassword(null);
			seller.setIncome(-1);
		}
		return convertToDto(seller);
	}
	
	
	/**
	 * Formal business method for creating a new seller 
	 * @param name The preferred display name
	 * @param email The PK, as well as account identifier
	 * @param password
	 * @param profile A short biography /description of the seller
	 * @return SellerDto
	 * @throws IllegalArgumentException
	 * @author bokunzhao
	 * 
	 * Test: http://localhost:8080/create-seller?name=someone&email=some@mail&password=123&profile=GOAT
	 */
	@PostMapping(value = {"/create-seller", "/create-seller/"})
	public SellerDto createSeller(
			@RequestParam("name") String name, 
			@RequestParam("email")String email,
			@RequestParam("password") String password, 
			@RequestParam("profile") String profile) 
					throws IllegalArgumentException{
		Seller seller = service.createSeller(name, email, password, profile);
		return convertToDto(seller);		
	}
	
	
	/**
	 * @param email
	 * @param password
	 * @return : a list of ProductDto containing all the Seller's products 
	 * @throws Exception : If seller with such email does not exist, or a wrong password is provided.
	 * @author chenxuan-zhou
	 */	
	
	@GetMapping(value = { "/seller-products"})
	public List<ProductDto> getSellerProducts(
			@RequestParam("email") String email,
			@RequestParam("password") String password
			) throws Exception {
		Seller seller = service.getSeller(email);
		if (seller == null) {
			throw new Exception("Seller does not exist."); // TODO : name this exception
		}
		if (!password.equals(seller.getPassword())) {
			throw new Exception("Wrong password."); // TODO : name this exception
		}
		return ProductController.convertToDto(seller.getProducts());
		
	}
	
	
	/**
	 * Test: http://localhost:8080/change-seller-name?seller=some@mail&new-name=mar won
	 * @param email
	 * @param name
	 * @throws IllegalArgumentException
	 */
	@PutMapping(value = {"/change-seller-name","/change-product-name"})
	public void changeSellerName(
			@RequestParam("seller") String email,
			@RequestParam("new_name") String name			
			) throws IllegalArgumentException {	
		service.changeName(email, name);
	}
	
	@PutMapping(value = {"/change-seller-profile","/change-product-profile"})
	public void changeSellerProfile(
			@RequestParam("seller") String email,
			@RequestParam("new_profile") String profile			
			) throws IllegalArgumentException {	
		service.changeProfile(email, profile);
	}
	
	@PutMapping(value = {"/change-seller-password","/change-product-password"})
	public void changeSellerPassword(
			@RequestParam("seller") String email,
			@RequestParam("new_password") String password			
			) throws IllegalArgumentException {	
		service.changePassword(email, password);
	}
	
	@PutMapping(value = {"/change-seller-income","/change-product-income"})
	public void addIncome(
			@RequestParam("seller") String email,
			@RequestParam("new_income") int income		
			) throws IllegalArgumentException {	
		service.addIncome(email, income);
	}
	
	@PutMapping(value = {"/withdrawl-seller","/withdrawl-seller"})
	public void withdrawl(
			@RequestParam("seller") String email
			) throws IllegalArgumentException {	
		service.withdrawl(email);
	}
	
	@PutMapping(value = {"/login","/login"})
	public void login(
			@RequestParam("email") String email,
			@RequestParam("password") String password			
			) throws IllegalArgumentException {	
		service.login(email, password);
	}
	
	@PutMapping(value = {"/logout","/logout"})
	public void logout(
			@RequestParam("email") String email
			) throws IllegalArgumentException {	
		service.logout(email);
	}
	
	/**
	 * @param seller 
	 * @return a SellerDto with all information in the seller
	 * @author chenxuan-zhou
	 */
	protected static SellerDto convertToDto(Seller seller) {
		if(seller==null) {
			throw new IllegalArgumentException("There is no such Seller!");
		}
		SellerDto sellerDto = new SellerDto(
				seller.getName(), seller.getPassword(), seller.getEmail(), seller.getProfile(), seller.getIncome(),
				ManagerController.convertStatus(seller.getStatus()), 
				ProductController.convertToDto(seller.getProducts()), 
				OrderController.convertToDto(seller.getOrders())
				);
		return sellerDto;
	}
	

}
