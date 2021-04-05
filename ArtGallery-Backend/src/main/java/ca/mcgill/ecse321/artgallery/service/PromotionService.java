package ca.mcgill.ecse321.artgallery.service;

import ca.mcgill.ecse321.artgallery.dao.ManagerRepository;
import ca.mcgill.ecse321.artgallery.dao.ProductRepository;
import ca.mcgill.ecse321.artgallery.dto.PromotionDto;
import ca.mcgill.ecse321.artgallery.model.Account;
import ca.mcgill.ecse321.artgallery.model.Manager;
import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import ca.mcgill.ecse321.artgallery.dao.PromotionRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 * Promotion Service
 * @author Yutian Fu
 */

@Service
public class PromotionService {

	@Autowired
	private PromotionRepository promotionRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ManagerRepository managerRepository;

	/**
	 * Successfully create a promotion
	 * @param manager
	 * @param id
	 * @param start_date
	 * @param end_date
	 * @param product
	 * @return Promotion
	 */
	@Transactional
	public Promotion createPromotion(Manager manager,Long id, Date start_date, Date end_date,Product product){

		if(product ==null){
				throw new IllegalArgumentException("Please add at least one product");
			}
		if(manager==null){
			throw new IllegalArgumentException("Please log in to your account");
		}
		if(manager.getStatus() == Account.AccountStatus.Blocked){
			throw new IllegalArgumentException("Your account is blocked and you cannot do this action");
		}
		if(start_date==null){
			throw new IllegalArgumentException("Please specify the start date");
		}
		if(end_date==null){
			throw new IllegalArgumentException("Please specify the end date");
		}

				Promotion promt = new Promotion();
				promt.setStartDate(start_date);
				promt.setEndDate(end_date);
				promt.setId(id);
				List<Product> promt_products = new ArrayList<>();
				promt_products.add(product);
				promt.setProduct(promt_products);
				promt.setManager(manager);
				product.setPromotion(promt);
				System.out.println("Here P"+promt.getProduct().size());


				return promotionRepository.save(promt);
			}


	/**
	 * Get all promotions
	 * @return List<Promotion>
	 */
	@Transactional
	public List<Promotion> getAllPromotions(){
		return toList(promotionRepository.findAll());
	}

	/**
	 * Get promotion by id
	 * @param id
	 * @return Promotion
	 */
	@Transactional
	public Promotion getPromotion(Long id){
		Promotion promt= promotionRepository.findPromotionById(id);


		if(promt==null){
			throw new EntityNotFoundException("Could not find a promotion with ID " + id);
		}



		return promt;

	}
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * Get all promotions by start_date
	 * @param start_date
	 * @return List<Promotion>
	 */
	@Transactional
	public List<Promotion> getPromotionByStartDate(Date start_date){
		if(start_date==null){
			throw new IllegalArgumentException("Please specify start date");
		}
		Iterable<Promotion> iterable = promotionRepository.findPromotionByStartDate(start_date);
		List<Promotion> promotionList = new ArrayList<>();
		for ( Promotion t : iterable) {
			promotionList.add(t);
		}
		return promotionList;
	}

	/**
	 * Get all promotions by end_date
	 * @param end_date
	 * @returnList<Promotion>
	 * 
	 */
	@Transactional
	public List<Promotion> getPromotionByEndDate(Date end_date){
		if(end_date==null){
			throw new IllegalArgumentException("Please specify end date");
		}
		Iterable<Promotion> iterable = promotionRepository.findPromotionByEndDate(end_date);
		List<Promotion> promotionList = new ArrayList<>();
		for ( Promotion t : iterable) {
			promotionList.add(t);
		}
		return promotionList;
	}

	/**
	 *
	 * @param id
	 * @param end_date
	 * @return Promotion
	 *
	 */
	@Transactional
	public  Promotion  extendPromotionDate(long id,Date end_date){
		if( end_date ==null){
			throw new IllegalArgumentException("Please specify the extendDate");
		}
		Promotion promt= promotionRepository.findPromotionById(id);
		promt.setEndDate(end_date);
		return promt;

	}
	@Transactional
	public  List<Promotion> delete(long id){
		List<Promotion> promotions=toList(promotionRepository.findAll());


		for(Promotion a : promotions){
			if(a.getId()==id){
				Manager manager=a.getManager();
				manager.getPromotion().remove(a);

				promotionRepository.delete(a);

				break;
			}

		}
		List<Promotion> newpromotions= toList(promotionRepository.findAll());


		return newpromotions;

	}







}
