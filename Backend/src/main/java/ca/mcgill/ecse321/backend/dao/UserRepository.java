package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321.backend.model.User;
import ca.mcgill.ecse321.backend.model.UserRole;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	User findByIdNumber(int id);
	User findUserByUserRole(UserRole userRole);
	User findUserByName(String name);
}
