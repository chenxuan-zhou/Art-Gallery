package ca.mcgill.ecse321.artgallery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.model.Account.AccountStatus;
import ca.mcgill.ecse321.artgallery.model.Seller;


@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Transactional
	public Seller createSeller(String name, String email, String password, String profile) {
		// check if no email
		if ( email==null || email.isEmpty() ) {
			throw new IllegalArgumentException("Email not provided.");
		}
		
		// check if no password
		if ( password==null || password.isEmpty() ) {
			throw new IllegalArgumentException("Password not provided.");
		}
		
		// check if email existed 
		if (sellerRepository.findSellerByEmail(email) != null) {
			throw new IllegalArgumentException("Account with the email already exists.");
		}
		
		
		Seller seller = new Seller();
		
		// Set parameters
		seller.setName(name);
		seller.setEmail(email);
		seller.setPassword(password);
		seller.setProfile(profile);

		// Default values for a new seller account
		seller.setStatus(AccountStatus.New);
		seller.setIncome(0d);

		sellerRepository.save(seller);
		return seller;	
	}
	

	@Transactional
	public Seller getSeller(String email) {
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) throw new IllegalArgumentException("Can not find seller with this email");
		return seller; // null if not found
	}
	
	@Transactional
	public void changeName(String email,String name) {
		if(name==null || name.isEmpty()) {
			throw new IllegalArgumentException("Name not provided.");
		}
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) {
			throw new IllegalArgumentException("Can not find seller with this email");
		}
		seller.setName(name);
		sellerRepository.save(seller);
	}
	

	@Transactional
	public void addIncome(String email, double income) {
		Seller seller = sellerRepository.findSellerByEmail(email);		
		if(seller==null) {
			throw new IllegalArgumentException("Can not find seller with this email");
		}
		double origin = seller.getIncome();
		origin += income;
		seller.setIncome(origin);
		sellerRepository.save(seller);
	}
	
	@Transactional
	public void withdrawl(String email) {
		Seller seller = sellerRepository.findSellerByEmail(email);		
		if(seller==null) {
			throw new IllegalArgumentException("Can not find seller with this email");
		}
		double zero = 0.00;
		seller.setIncome(zero);
		sellerRepository.save(seller);
	}
	
	@Transactional
	public void changeProfile(String email,String profile) {
		// profile can be null, not IllegalArgumentException
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) {
			throw new IllegalArgumentException("Can not find seller with this email");
		}
		seller.setProfile(profile);

		sellerRepository.save(seller);
	}	
	
	@Transactional
	public void changePassword(String email,String newPassword) {
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) throw new IllegalArgumentException("Can not find seller with this email");	
		if (seller.getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");	
		if(newPassword==null || newPassword.length()==0) throw new IllegalArgumentException("Illegal password");
		seller.setPassword(newPassword);
		sellerRepository.save(seller);
	}

	/**
	 * This can be used to login or logout. Whenever login input (Active as parameter), whenever logout input (inactive)
	 * @param email
	 * @param status
	 */
	@Transactional
	public void login(String email,String password) {
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) throw new IllegalArgumentException("Can not find seller with this email");	
		if (seller.getStatus().equals(AccountStatus.Blocked)) throw new IllegalArgumentException("Account blocked.");
		if (!seller.getPassword().equals(password)) throw new IllegalArgumentException("Wrong password.");
		seller.setStatus(AccountStatus.Active);
		sellerRepository.save(seller);
	}

	
	@Transactional
	public void logout(String email) {
		Seller seller = sellerRepository.findSellerByEmail(email);
		if(seller==null) throw new IllegalArgumentException("Can not find seller with this email");	
		if (seller.getStatus()!=AccountStatus.Active) throw new IllegalArgumentException("Must login first");
		seller.setStatus(AccountStatus.Inactive);
		sellerRepository.save(seller);
	}

		

	
	@Transactional
	public List<Seller> getAllSellers() {
		return toList(sellerRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
