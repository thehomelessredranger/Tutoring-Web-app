package ca.mcgill.ecse321.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Availability;
import ca.mcgill.ecse321.backend.model.Tutor;

@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, Integer>{
	List<Availability> findAvailabilityByTutor(Tutor tutor);
	Availability findById(int id);
}
