package ca.mcgill.ecse321.artgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.artgallery.controller.PromotionController;
import ca.mcgill.ecse321.artgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.artgallery.dao.ManagerRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dao.SellerRepository;
import ca.mcgill.ecse321.artgallery.model.*;
import ca.mcgill.ecse321.artgallery.service.*;

/**
 * 
 * @author cui lide
 * 
 */
@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;

	/**
	 * create a new manager
	 * 
	 * @param email
	 * @param name
	 * @param password
	 * @param status
	 * @return
	 */
	@Transactional
	public Manager createManager(String email, String name, String password, Account.AccountStatus status) {
		if (managerRepository.findManagerByEmail(email) != null) {
			throw new IllegalArgumentException("Account already exists.");
		}

		Manager testManager = new Manager();
		testManager.setEmail(email);
		testManager.setName(name);
		testManager.setPassword(password);
		testManager.setStatus(status); // change default as new

		managerRepository.save(testManager);
		return testManager;
	}

	/**
	 * Find a manager by email
	 * 
	 * @param email
	 * @return
	 */
	@Transactional
	public Manager findManager(String email) {
		if (managerRepository.findManagerByEmail(email) == null) {
			throw new IllegalStateException("account does not exists");
		}

		Manager testManager = managerRepository.findManagerByEmail(email);
		return testManager;
	}

	/**
	 * get all managers
	 * 
	 * @return
	 */
	@Transactional
	public List<Manager> getAllManagers() {
		return toList(managerRepository.findAll());
	}
	
	/*
	 * *********************** Configuration code *************************
	 * 
	 */

	/**
	 * log in
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Transactional
	public boolean login(String email, String password) {
		Manager testManager = managerRepository.findManagerByEmail(email);

		if (testManager == null) {
			throw new IllegalArgumentException("account does not exists");
		}

		if (!testManager.getPassword().equals(password)) {

			throw new IllegalArgumentException("Incorrect password.");

		}

		testManager.setStatus(Account.AccountStatus.Active);
		managerRepository.save(testManager);
		return true;
	}

	/**
	 * log out email, s.t. user should login first
	 * 
	 * @param email
	 * @param token
	 * @return
	 */
	@Transactional
	public boolean logout(String email) {
		Manager testManager = managerRepository.findManagerByEmail(email);

		if (managerRepository.findManagerByEmail(email) == null) {
			throw new IllegalArgumentException("account does not exists");
		}

		testManager.setStatus(Account.AccountStatus.Inactive);
		managerRepository.save(testManager);

		return true;

	}
	
	/**
	 * change manager name
	 * @param email
	 * @param newName
	 * @return
	 */
	@Transactional
	public boolean changeName(String email, String newName) {
		Manager testManager = this.findManager(email);
		
		if (testManager.getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}
		
		testManager.setName(newName);
		managerRepository.save(testManager);
		
		return true;
	}
	
	/**
	 * change manager password
	 * @param email
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Transactional
	public boolean changePassword(String email, String oldPassword, String newPassword) {
		Manager testManager = this.findManager(email);
		
		if (testManager.getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}
		
		if (!testManager.getPassword().equals(oldPassword)) {
			throw new IllegalArgumentException("Invalid password, fail to reset password");
		}
		
		testManager.setPassword(newPassword);
		managerRepository.save(testManager);	
		
		return true;
	}
	
	/**
	 * delete a manager
	 * @param email
	 * @param password
	 * @return
	 */
	@Transactional
	public boolean deleteManager(String email, String password) {
		
		Manager testManager = managerRepository.findManagerByEmail(email);

		if (testManager == null) {
			throw new IllegalArgumentException("account does not exists");
		}

		if (!testManager.getPassword().equals(password)) {

			throw new IllegalArgumentException("Incorrect password. Fail to delete.");

		}
		
		managerRepository.delete(testManager);
		
		return true;
	}

	/*
	 * *********************** Service code *************************
	 * 
	 */
	/**
	 * suspend a product
	 * 
	 * @param id
	 * @return
	 */
	public boolean suspendProduct(String adminEmail, Long id) {
		
		if (this.findManager(adminEmail).getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}

		Product aProduct = productRepository.findProductById(id);
		aProduct.setProductStatus(Product.ProductStatus.Suspended);
		productRepository.save(aProduct);
		return true;
	}
	
	/**
	 * put a product back to selling state
	 * @param adminEmail
	 * @param id
	 * @return
	 */
	public boolean approveProduct(String adminEmail, Long id) {
		
		if (this.findManager(adminEmail).getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}

		Product aProduct = productRepository.findProductById(id);
		aProduct.setProductStatus(Product.ProductStatus.Selling);
		productRepository.save(aProduct);
		return true;
	}

	/**
	 * suspend an account
	 * 
	 * @param email
	 * @return
	 */
	// @SuppressWarnings("unused")
	public boolean suspendAccount(String adminEmail, String email) {

		if (this.findManager(adminEmail).getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}

		Seller aSeller = sellerRepository.findSellerByEmail(email);
		Customer aCustomer = customerRepository.findCustomerByEmail(email);

		if (aSeller == null && aCustomer == null) {
			throw new IllegalStateException("account does not exists");
		}

		if (aSeller != null) {
			aSeller.setStatus(Account.AccountStatus.Blocked);
			sellerRepository.save(aSeller);
		} 
		if (aCustomer != null) {
			aCustomer.setStatus(Account.AccountStatus.Blocked);
			customerRepository.save(aCustomer);
		}

		return true;
	}
	
	/**
	 * put a product back to inactive
	 * @param adminEmail
	 * @param email
	 * @return
	 */
	public boolean approveAccount(String adminEmail, String email) {

		if (this.findManager(adminEmail).getStatus() != Account.AccountStatus.Active) {
			throw new IllegalStateException("Admin must login to operate");
		}

		Seller aSeller = sellerRepository.findSellerByEmail(email);
		Customer aCustomer = customerRepository.findCustomerByEmail(email);

		if (aSeller == null && aCustomer == null) {
			throw new IllegalStateException("account does not exists");
		}

		if (aSeller != null) {
			aSeller.setStatus(Account.AccountStatus.Inactive);
			sellerRepository.save(aSeller);
		} 
		if (aCustomer != null) {
			aCustomer.setStatus(Account.AccountStatus.Inactive);
			customerRepository.save(aCustomer);
		}

		return true;
	}

	/*
	 * *********************** Helper function *************************
	 * 
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
