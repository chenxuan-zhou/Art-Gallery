package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "seller_data", path = "seller_data")
public interface SellerRepository extends CrudRepository<Seller, String> {

	Seller findSellerByEmail(String email);

	Iterable<Seller> findSellerByProfile(String profile);

	Iterable<Seller> findSellerByIncome(double income);

	void deleteAccountByEmail(String email);

}
