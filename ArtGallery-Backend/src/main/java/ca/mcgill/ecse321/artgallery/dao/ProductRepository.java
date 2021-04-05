package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "product_data", path = "product_data")
public interface ProductRepository extends CrudRepository<Product, Long> {

	Product findProductById(long id);

	 Iterable<Product> findByproductStatus(Product.ProductStatus productStatus);

	 Iterable<Product> findByName(String name);

	 Iterable<Product> findByPrice(Integer price);

	 //void deleteByProduct_id(long id);
	 void deleteById(long id);
}
