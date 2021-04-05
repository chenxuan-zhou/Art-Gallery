package ca.mcgill.ecse321.artgallery.dto;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;


public class ManagerDto extends AccountDto {
	
	private Set<PromotionDto> promotion;

	
	// Default Constructor
	public ManagerDto() {
		
	}
	
	public ManagerDto(String email, String name, String password, AccountDto.AccountStatus status) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.status = status;
	}

	public Set<PromotionDto> getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Set<PromotionDto> promotion) {
		this.promotion = promotion;
	}



}
