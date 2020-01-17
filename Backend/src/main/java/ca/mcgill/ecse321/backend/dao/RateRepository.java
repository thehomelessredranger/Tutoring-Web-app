package ca.mcgill.ecse321.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Course;
import ca.mcgill.ecse321.backend.model.Rate;
import ca.mcgill.ecse321.backend.model.Tutor;

@Repository
public interface RateRepository extends CrudRepository<Rate, Integer>{
	Rate findByRateId(Integer idNumber);
	List<Rate> findRateByCourse(Course course);
	List<Rate> findRateByTutor(Tutor tutor);
}
