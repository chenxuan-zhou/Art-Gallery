package ca.mcgill.ecse321.artgallery.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.artgallery.dto.AccountDto;
import ca.mcgill.ecse321.artgallery.dto.ManagerDto;
import ca.mcgill.ecse321.artgallery.dto.PromotionDto;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import ca.mcgill.ecse321.artgallery.service.ManagerService;

/**
 * 
 * @author Lide
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class ManagerController {
	
	@Autowired
	private ManagerService service;
	
	/**
	 * http://localhost:8080/managers
	 * @return a list of managers
	 */
	@GetMapping(value = { "/managers", "/managers/" })
	public List<ManagerDto> getAllManagers() {
		return service.getAllManagers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	/**
	 * pass postman test
	 * http://localhost:8080/create-manager?email=test@mail&name=testManager&password=123
	 */
	@PostMapping(value = { "/create-manager", "/create-manager/" })
	public ManagerDto creatManager(@RequestParam("email") String email, 
			@RequestParam("name") String name, 
			@RequestParam("password") String password) throws IllegalArgumentException{
		Manager aManager = service.createManager(email, name, password, Account.AccountStatus.New);
		return convertToDto(aManager);
	}
	
	/**
	 * find a manager by email
	 * http://localhost:8080/find-manager?email=test@mail
	 * @param email
	 * @return
	 */
	@GetMapping(value= {"/find-manager", "/find-manager/"})
	public ManagerDto findManager(@RequestParam("email") String email) {
		Manager aManager = service.findManager(email);
		return convertToDto(aManager);
	}
	
	/**
	 * log in a manager
	 * http://localhost:8080/manager-login?email=test@mail&password=123
	 * @param email
	 * @param password
	 * @return
	 */
	@PutMapping(value = {"/manager-login", "/mananger-login/"})
	public boolean managerLogin(@RequestParam("email") String email,
			@RequestParam("password") String password)  {	
		return service.login(email, password);
	}
	
	/**
	 * log out a manager
	 * http://localhost:8080/manager-logout?email=test@mail
	 * @param email
	 * @return
	 */
	@PutMapping(value = {"/manager-logout", "/mananger-logout/"})
	public boolean managerLogout(@RequestParam("email") String email)  {	
		return service.logout(email);

	}
	
	/**
	 * 
	 * http://localhost:8080/manager-changePassword?email=test@mail&password=123&newPassword=123
	 * @param email
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@PutMapping(value = {"/manager-changePassword", "/mananger-changePassword/"})
	public boolean managerChangePassword(@RequestParam("email") String email,
			@RequestParam("password") String oldPassword,
			@RequestParam("newPassword") String newPassword)  {	
		return service.changePassword(email, oldPassword, newPassword);
	}
	
	/**
	 * http://localhost:8080/manager-changeName?email=test@mail&newName=testManagerB
	 * @param email
	 * @param newName
	 * @return
	 */
	@PutMapping(value = {"/manager-changeName", "/mananger-changeName/"})
	public boolean managerChangeName(@RequestParam("email") String email,
			@RequestParam("newName") String newName)  {	
		return service.changeName(email, newName);
	}
	
	
	/**
	 * delete a manager
	 * http://localhost:8080/delete-manager?email=test@mail&password=123
	 * this is the last step of api test
	 * @param email
	 * @param password
	 * @return
	 */
	@DeleteMapping(value = {"/delete-manager","/delete-manager/"})
	public boolean managerDelete(@RequestParam("email") String email,
			@RequestParam("password") String password)  {	
		return service.deleteManager(email, password);
	}
	
	
	
	/**
	 * suspend a product
	 * before we need
	 * create a seller ->
	 * http://localhost:8080/create-seller?name=someone&email=some@mail&password=123&profile=GOAT
	 * login the seller ->
	 * http://localhost:8080/login?email=some@mail&password=123
	 * then create a product ->
	 * http://localhost:8080/create-product?status=Selling&delivery=Both&name=drawing&seller=some@mail&price=100&picture=
	 * 
	 * http://localhost:8080/suspend-product?managerEmail=test@mail&productID=1 (ID returned by createProduct)
	 * @param id
	 * @return
	 */
	@PutMapping(value = {"/suspend-product", "/suspend-product/"})
	public boolean suspendProduct(@RequestParam("managerEmail") String adminEmail,
			@RequestParam("productID") long id) {
		return service.suspendProduct(adminEmail, id);
	}
	
	/**
	 * 
	 * @param adminEmail
	 * @param id
	 * @return
	 */
	@PutMapping(value = {"/approve-product", "/approve-product/"})
	public boolean approveProduct(@RequestParam("managerEmail") String adminEmail,
			@RequestParam("productID") long id) {
		return service.approveProduct(adminEmail, id);
	}
	
	/**
	 * suspend an account
	 * http://localhost:8080/suspend-account?managerEmail=test@mail&email=some@mail
	 * @param email
	 * @return
	 */
	@PutMapping(value = {"/suspend-account", "/suspend-account/"})
	public boolean suspendAccount(@RequestParam("managerEmail") String adminEmail,
			@RequestParam("email") String email) {
		return service.suspendAccount(adminEmail, email);
	}
	
	/**
	 * 
	 * @param adminEmail
	 * @param email
	 * @return
	 */
	@PutMapping(value = {"/approve-account", "/approve-account/"})
	public boolean appproveAccount(@RequestParam("managerEmail") String adminEmail,
			@RequestParam("email") String email) {
		return service.approveAccount(adminEmail, email);
	}
	

	
	/* 
	 * **************** helper function **********************
	 */
	
	private ManagerDto convertToDto(Manager aManager) {
		if (aManager == null) {
			throw new IllegalArgumentException("There is no such Manager!");
		}
		ManagerDto managerDto = new ManagerDto(aManager.getEmail(),aManager.getName(),aManager.getPassword(),convertStatus(aManager.getStatus()));
		return managerDto;
	}
	
    private PromotionDto convertToDto(Promotion promt) {
        if (promt == null) {
            throw new IllegalArgumentException("There is no such Promotion");
        }
        PromotionDto promotionDto = new PromotionDto(promt.getId(),promt.getStartDate(),promt.getEndDate());
        return promotionDto;
    }
	
	static AccountDto.AccountStatus convertStatus(Account.AccountStatus state){
		switch(state) {
		case New : return AccountDto.AccountStatus.New;
		case Blocked: return AccountDto.AccountStatus.Blocked;
		case Active: return AccountDto.AccountStatus.Active;
		case Inactive: return AccountDto.AccountStatus.Inactive;
		default: return null;
		}
	}
}
