package ca.mcgill.ecse321.artgallery.dao;

import ca.mcgill.ecse321.artgallery.model.Manager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, String> {
	Manager findManagerByEmail(String email);

	void deleteAccountByEmail(String email);
}
