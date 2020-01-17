package ca.mcgill.ecse321.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Course;
import ca.mcgill.ecse321.backend.model.Session;
import ca.mcgill.ecse321.backend.model.Tutor;

@Repository
public interface CourseRepository extends CrudRepository<Course, String>{
	
	Course findByCourseName(String courseName);
	
	Course findByCourseNumber(int courseNumber);
	
	List<Course> findCourseByTutor(Tutor tutor);
	
	List<Course> findCourseBySubject(String subject);
	
	Course findCourseBySession(Session session);
	
	Course findByCourseId(String id);
	

}
