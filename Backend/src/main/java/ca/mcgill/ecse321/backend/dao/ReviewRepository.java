package ca.mcgill.ecse321.backend.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Review;
import ca.mcgill.ecse321.backend.model.Student;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer>{
	Review findReviewByReviewId(Integer reviewId);
	List<Review> findReviewByStudent(Student student);
	Set<Review> findAll();
}
