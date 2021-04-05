package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
	Customer findCustomerByEmail(String email);

	Iterable<Customer> findCustomerByBalance(double balance);

	void deleteAccountByEmail(String email);
}
