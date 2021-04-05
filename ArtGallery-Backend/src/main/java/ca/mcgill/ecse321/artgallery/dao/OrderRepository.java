package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Order;
import ca.mcgill.ecse321.artgallery.model.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	Order findById(long id);
	Order findOrderByProduct(Product product);
	Iterable<Order> findByAddress(String address);

	void deleteById(long id);

}
