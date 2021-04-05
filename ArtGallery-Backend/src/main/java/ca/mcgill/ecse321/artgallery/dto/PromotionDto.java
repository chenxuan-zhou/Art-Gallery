package ca.mcgill.ecse321.artgallery.dto;



import java.sql.Date;
import java.util.List;

public class PromotionDto {


	// Promotion Attributes

	private long id;
	private Date startDate;
	private Date endDate;
	private List<Long> product;
	private ManagerDto managerDto;




	public PromotionDto(long id, Date startDate, Date endDate,List<Long> productDto,ManagerDto managerDto) {
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.product=productDto;
		this.managerDto=managerDto;


	}


	public PromotionDto(long id, Date startDate, Date endDate) {
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;





	}


	public long getId() {
		return id;
	}

	public void setId(long aId) {
		id = aId;
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

	public List<Long> getProductDto() {
		return this.product;
	}

	public boolean setProductDto(List<Long> product) {
		if(product==null){
			return false;
		}
		this.product = product;
		return true;
	}

	public ManagerDto getManager() {
		return this.managerDto;
	}

	public void setManagerDto(ManagerDto aManager) {
		if(this.managerDto!=null) return;
		this.managerDto = aManager;
	}


}