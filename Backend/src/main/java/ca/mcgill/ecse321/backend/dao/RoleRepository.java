package ca.mcgill.ecse321.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Course;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.Rate;
import ca.mcgill.ecse321.backend.model.Session;
import ca.mcgill.ecse321.backend.model.Student;
import ca.mcgill.ecse321.backend.model.Tutor;
import ca.mcgill.ecse321.backend.model.User;
import ca.mcgill.ecse321.backend.model.UserRole;

@Repository
public interface RoleRepository extends CrudRepository<UserRole, Integer>{

	UserRole findByUser(User user);
	UserRole findByUserIdNumber(Integer ID);
	Optional<UserRole> findById(Integer ID);

	List<Tutor> findTutorByCourse(Course course);
	List<Tutor> findTutorByManager(Manager manager);
	List<Tutor> findTutorByisVerified(boolean isVerified);
	Tutor findTutorBySession(Session session);
	Tutor findTutorByRate(Rate rate);


//	Student findStudentBySession(SingleSession singleSession);
//	List<Student> findStudentBySession(GroupSession groupSession);
	List<Student> findStudentBySession(Session Session);

}
