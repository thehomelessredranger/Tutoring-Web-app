package ca.mcgill.ecse321.backend.dao;

import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321.backend.model.Qualification;
import ca.mcgill.ecse321.backend.model.Tutor;

/*
 * Implementation for qualification
 * @author The Elk
 * */

@Repository
public interface QualificationRepository extends CrudRepository<Qualification, Integer>{
	Set<Qualification> findQualificationByTutor(Tutor tutor);
	
}
