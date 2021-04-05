package ca.mcgill.ecse321.artgallery.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "manager")
public class Manager extends Account {


	// Manager Associations
	@OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Promotion> promotion;

	public Set<Promotion> getPromotion() {
		return this.promotion;
	}
	public void setPromotion(Set<Promotion> promotion) {
		this.promotion = promotion;
	}


}