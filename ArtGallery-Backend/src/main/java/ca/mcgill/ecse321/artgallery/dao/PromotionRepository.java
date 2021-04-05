package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Product;
import ca.mcgill.ecse321.artgallery.model.Promotion;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "promotion_data", path = "promotion_data")
public interface PromotionRepository extends CrudRepository<Promotion, Long> {
    Promotion findPromotionById(Long id);
    Iterable<Promotion> findPromotionByStartDate(Date start_date);
    Iterable<Promotion> findPromotionByEndDate(Date end_date);



}