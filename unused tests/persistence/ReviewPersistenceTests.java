package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.*;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewPersistenceTests {

	@Autowired
	private MasterService service;

	@Autowired
	private ReviewRepository reviewDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private RoleRepository roleDao;

	private Review review;
	private Student student;
	private User studentUser;
	private Tutor tutor;
	private User tutorUser;
	private Manager manager;
	private User managerUser;
	
	private String typeTutor = "Tutor";
	private String typeStudent = "Student";
	private String typeManager = "Manager";


	private List<Review> studentReview = new ArrayList<>();
	private List<Review> tutorReview;

	private static int reviewId;
	private static final String feedback = "feedback";
	private static final Rating rating = Rating.FIVE_STAR;


	private static final int userId = 1;
	private static final int userId2 = 3;
	private static final int managerId = 2;
	private static final String userName = "a";
	private static final String userName2 = "b";
	private static final String userName3 = "c";
	private static final String pass = "pass";


	@After
	public void clearDatabase() {
		reviewDao.deleteAll();
		roleDao.deleteAll();
		userDao.deleteAll();
	}

	// @Before
	// public void setMockOutput() {
	// 	when(reviewDao.findReviewByReviewId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
	// 		if(invocation.getArgument(0).equals(reviewId)) {
	// 			return review;
	// 		}
	// 		return null;
	// 	});
	// 	when(reviewDao.findReviewByStudent(any())).thenAnswer((InvocationOnMock invocation) -> {
	// 		if(invocation.getArgument(0).equals(student)) {
	// 			return studentReview;
	// 		}
	// 		return null;
	// 	});
	// 	when(reviewDao.findReviewByTutor(any())).thenAnswer((InvocationOnMock invocation) -> {
	// 		if(invocation.getArgument(0).equals(tutor)) {
	// 			return tutorReview;
	// 		}
	// 		return null;
	// 	});
	// }
	//
	// @Before
	// public void setUpMocks() {
	// 	studentUser = mock(User.class);
	// 	studentUser = service.createUser(userId, userName, pass, "Student");
	// 	tutorUser = mock(User.class);
	// 	tutorUser = service.createUser(userId2, userName2, pass, "Tutor");
	// 	tutor = mock(Tutor.class);
	// 	tutor =(Tutor) tutorUser.getUserRole();
	// 	student = mock(Student.class);
	// 	student = (Student) studentUser.getUserRole();
	// 	review = mock(Review.class);
	// 	review = service.createReview(student, tutor, feedback, rating);
	// 	studentReview.add(review);
	// }
	
	@Before
	public void setup() {
		reviewDao.deleteAll();
		roleDao.deleteAll();
		userDao.deleteAll();
		studentUser = service.createUser(userId, userName, pass, typeStudent);
		managerUser = service.createUser(managerId, userName3, pass, typeManager);
		tutorUser = service.createUser(userId2, userName2, pass, typeTutor);
		student = (Student) studentUser.getUserRole();
		tutor =(Tutor) tutorUser.getUserRole();
		review = service.createReview((Student) service.getUser(userId).getUserRole(), (Tutor) service.getUser(userId2).getUserRole(), feedback, rating);
		Review aReview = service.getReviews(student).get(0);
		reviewId = aReview.getId();
	}
	
	@Test
	public void TestTutorcreation() {
		tutorUser = service.createUser(userId2+1, userName, pass, typeTutor);
		assertNotNull(tutorUser);
		assertTrue(tutorUser.getUserRole() instanceof Tutor);
	}
	
	@Test
	public void testReviewCreation() {
		assertNotNull(review);
		assertNotNull(service.getReview(reviewId));
		assertNotNull(service.getAllReviews());
		assertNotNull(service.getReviews(student));
		assertNotNull(service.getReviews(tutor));
	}

	@Test
	public void queryReviewByStudent() {
		Review a = service.getReviews(student).get(0);

		assertEquals(feedback, a.getFeedback());
		assertEquals(rating, a.getRating());
		assertEquals(review.getStudent().getId(), a.getStudent().getId());
		assertEquals(review.getStudent().getUser().getName(), a.getStudent().getUser().getName());
		assertEquals(reviewId,a.getId()+0);
	}
	
	@Test
	public void queryReviewNotFound() {
		assertNull(service.getReview(reviewId+1));
	}
	
	@Test
	public void testOverwriteReview() {
		String msg = "new feedback";
		Review review = service.createReview((Student) service.getUser(userId).getUserRole(), (Tutor) service.getUser(userId2).getUserRole(), msg, rating);
		assertEquals(msg, service.getReview(reviewId).getFeedback());
	}

	@Test
	public void testReviewRemoval() {
		service.removeReview(reviewId);
		assertNull(service.getReview(reviewId));
	}
}
