package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.Mock;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.service.MasterService;
import ca.mcgill.ecse321.backend.model.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;


public class temp {
	
	@Mock
	private UserRepository userDao;
	@Mock
	private SessionRepository sessionDao;
	@Mock
	private AvailabilityRepository availDao;
	@Mock
	private CourseRepository courseDao;
	@Mock
	private RateRepository rateDao;
	@Mock
	private ReviewRepository reviewDao;
	@Mock
	private RoleRepository roleDao;
	
	
	
	@InjectMocks
	private MasterService service;

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	private UserRole role;
	private User user;
	private Course course;
	private Tutor tutor;
	private Manager manager;
	private Session session;
	private Rate rate;
	
	private List<Tutor> courseTutor;
	private List<Tutor> managerTutor;
	private List<Tutor> verifiedTutor;
	private List<Student> sessionStudents;
	
	private static final int userId = 1;
	private static final boolean verified = true;
	private static final Rating rating = Rating.THREE_STAR;
	

	@Before
	public void setMockOutput() {
		when(roleDao.findByUser(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(user)) {
				return role;
			}
			return null;
		});
		when(roleDao.findByUserIdNumber(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(userId)) {
				return role;
			}
			return null;
		});
		when(roleDao.findTutorByCourse(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(course)) {
				return courseTutor;
			}
			return null;
		});
		when(roleDao.findTutorByManager(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(manager)) {
				return managerTutor;
			}
			return null;
		});
		when(roleDao.findTutorByisVerified(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(verified)) {
				return verifiedTutor;
			}
			return null;
		});
		when(roleDao.findTutorBySession(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(session)) {
				return tutor;
			}
			return null;
		});
		when(roleDao.findTutorByRate(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(rate)) {
				return tutor;
			}
			return null;
		});
		when(roleDao.findStudentBySession(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(session)) {
				return sessionStudents;
			}
			return null;
		});


	}
}
