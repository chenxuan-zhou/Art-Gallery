/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.artgallery.model;

import java.sql.Date;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "promotion")
public class Promotion {


	// Promotion Attributes

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;

	@OneToMany(mappedBy = "promotion",fetch = FetchType.EAGER,orphanRemoval=true,cascade = {CascadeType.ALL})
	private List<Product> product;
	@ManyToOne(optional = false)
	@JoinColumn(name = "Manager_Email")
	private Manager manager;


	public long getId() {
		return id;
	}

	public boolean setId(long aId) {
		boolean wasSet = false;
		id = aId;
		wasSet = true;
		return wasSet;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date aStartDate) {

		startDate = aStartDate;


	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date aEndDate) {

		endDate = aEndDate;

	}

	public List<Product> getProduct() {
		return this.product;
	}

	public void setProduct(List<Product> product) {
		if (product == null) {
			return;
		}

		this.product = product;

	}

	public Manager getManager() {
		return this.manager;
	}

	public void setManager(Manager aManager) {
		if(this.manager!=null) return;
		this.manager = aManager;
	}


}