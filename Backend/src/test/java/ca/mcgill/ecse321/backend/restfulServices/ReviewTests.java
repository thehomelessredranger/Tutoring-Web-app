package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgill.ecse321.backend.dao.ReviewRepository;
import ca.mcgill.ecse321.backend.dao.RoleRepository;
import ca.mcgill.ecse321.backend.dao.UserRepository;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.MasterService;



@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private ReviewRepository reviewDao;

	@InjectMocks
	private MasterService service;
	

	private static final int tutorId = 0;
	private static final int studentId = 1;
	private static final int tutorId2 = 2;
	private static final int managerId = 3;
	private static final int noId = 10;
	private static final String feedbackA = "feedbackA";
	private static final String feedbackB = "feedbackB";
	private static final Rating rating1 = Rating.THREE_STAR;
	private static final Rating rating2 = Rating.FIVE_STAR;
	private static int reviewId = 4;


	private static Student student;
	private static Tutor tutor;
	private static Tutor tutor2;
	private static Review review;
	private static Review review2;

	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();
	private static Set<Review> allReviews = new HashSet<>();
	private static List<Review> tutorReviews = new ArrayList<>();

	private static final String name = "name";
	private static final String pass = "pass";


	@Before
	public void setMockOutput() {
		when(userDao.findByIdNumber(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(tutorId)) {
				return u1;
			} else if(invocation.getArgument(0).equals(studentId)) {
				return u2;
			} else if(invocation.getArgument(0).equals(tutorId2)) {
				return u3;
			} else  {
				return null;
			}
		});

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});

		when(reviewDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allReviews;
		});
		when(reviewDao.findReviewByStudent(any(Student.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Review> aList = new ArrayList<>();
			for (Review aReview: allReviews) {
				if(aReview.getStudent().equals(invocation.getArgument(0))) {
					aList.add(aReview);
				}
			}
			return aList;
		});
		when(reviewDao.findReviewByReviewId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(reviewId)) {
				return review;
			}
			return null;
		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(reviewId)) {
				review=null;
			} 
			return null;
		}).when(reviewDao).deleteById(anyInt());
	}

	@Before
	public void setUpMocks() {
		allReviews.clear();
		tutorReviews.clear();
		allRoles.clear();
		allUsers.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u4 = service.createUser(managerId, name, pass, "Manager");
		allRoles.add(u4.getUserRole());
		
		u1 = service.createUser(tutorId, name, pass, "Tutor");
		u2 = service.createUser(studentId, name, pass, "Student");
		u3 = service.createUser(tutorId2, name, pass, "Tutor");
		allUsers.add(u1);
		allUsers.add(u2);
		allUsers.add(u3);
		allUsers.add(u4);
		allRoles.add(u1.getUserRole());
		allRoles.add(u2.getUserRole());
		allRoles.add(u3.getUserRole());
		
		student = (Student) u2.getUserRole();
		tutor = (Tutor) u1.getUserRole();
		tutor2 = (Tutor) u3.getUserRole();

		assertEquals(0, service.getAllReviews().size());
		review = service.createReview((Student) service.getUser(studentId).getUserRole(), (Tutor) service.getUser(tutorId).getUserRole(), feedbackA, rating1);
		tutorReviews.add(review);
		allReviews.add(review);
		review.setId(reviewId);
	}


	@Test
	public void testReviewCreation() {
		assertNotNull(review);
		assertNotNull(review2);
	}


	@Test
	public void createReview() {
		assertEquals(feedbackA, review.getFeedback());

	}

	@Test
	public void getReview() {
		assertEquals(review, service.getReview(reviewId));
	}

	@Test
	public void getAverageReview() {

		review2 = service.createReview(student, tutor2, feedbackB, rating2);
		allReviews.add(review2);
		
		assertEquals(Rating.FOUR_STAR, service.getAverageRating(student));
	}
	
	@Test
	public void getAverageNoReview() {
		Student aStudent = new Student();
		try {
			service.getAverageRating(aStudent);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("No reviews found"));
		}
		aStudent.getReview();
		try {
			service.getAverageRating(aStudent);
			fail();
		} catch(IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("No reviews found"));
		}
	}

	@Test
	public void queryReviewByStudent() {
//		assertReview(service.getReviews(student).get(0), feedbackA, rating1, review.getStudent(), review.getStudent().getUser(), reviewId);
		assertEquals(review, service.getReviews(student).get(0));
	}
	@Test
	public void queryReviewByStudentAndTutor() {
		assertEquals(review, service.findReview(student, tutor));
		assertNull(service.findReview(student, tutor2));
		Student student2 = new Student();
		assertNull(service.findReview(student2, tutor));
	}
	@Test
	public void queryReviewNotFound() {
		assertNull(service.getReview(reviewId+10));
	}

	@Test
	public void testOverwriteReview() {
		String msg = "new feedback";
		service.createReview((Student) service.getUser(studentId).getUserRole(), (Tutor) service.getUser(tutorId).getUserRole(), msg, rating1);
		assertEquals(msg, review.getFeedback());
	}

	@Test
	public void testReviewRemoval() {
		assertNotNull(service.getReview(reviewId));
		service.removeReview(reviewId);
		assertNull(service.getReview(reviewId));
		try {
			service.removeReview(noId);
			fail();
		}
		catch(Exception e) {
			assertEquals(e.getMessage(),"No such review exists.");
		}
	}
	
	

	public void assertReview(Review r, String feedback, Rating rating, UserRole role, User user, int reviewId) {
		assertEquals(feedback, r.getFeedback());
		assertEquals(rating, r.getRating());
		assertEquals(role.getId(), r.getStudent().getId());
		assertEquals(user.getName(), r.getStudent().getUser().getName());
		assertEquals(reviewId, r.getId()+0);
	}

}
