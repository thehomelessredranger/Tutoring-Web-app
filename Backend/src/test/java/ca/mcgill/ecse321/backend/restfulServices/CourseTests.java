package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.MasterService;



@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private CourseRepository courseDao;

	@InjectMocks
	private MasterService service;

	private static final int tutorId = 0;
	private static final int studentId = 1;
	private static final int tutorId2 = 2;
	private static final int managerId = 3;
	private static final String noId = "10";
	
	private static final String subject = "subject";
	private static final String courseName = "subject321";
	private static final int courseNum = 321;
	private static String courseId;


	private static Tutor tutor;
	private static Course course;
	private static Session session;
	private static List<Course> courses = new ArrayList<>();
	
	

	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();

	private static final String name = "name";
	private static final String pass = "pass";


	@Before
	public void setMockOutput() {

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});

		when(courseDao.findByCourseNumber(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseNum)) {
				return course;
			}
			return null;
		});
		when(courseDao.findCourseByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Course> tutorCourses = new ArrayList<>();
			for (Course aCourse: courses) {
				if(aCourse.getTutor().contains(invocation.getArgument(0))) {
					tutorCourses.add(aCourse);
				}
			}
			return tutorCourses;
		});
		when(courseDao.findCourseBySession(any(Session.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(session)) {
				return course;
			}
			return null;
		});
		when(courseDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return courses;
		});
		when(courseDao.findByCourseId(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseId)) {
				return course;
			}
			return null;
		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseId)) {
				course = null;
			}
			return null;
		}).when(courseDao).deleteById(anyString());
	}

	@Before
	public void setUpMocks() {
		allRoles.clear();
		allUsers.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u4 = mock(User.class);
		session = mock(Session.class);
		
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
		
		tutor = (Tutor) u1.getUserRole();

		course = service.createCourse(subject, courseNum, courseName);
		service.addTutorToCourse(tutor, course);
		courseId = course.getCourseId();
		courses.clear();
		courses.add(course);
		
		
		
		session = new SingleSession();
		session.setCourse(course);
		course.getSession().add(session);
		
	}


	@Test
	public void testCourseCreation() {
		assertNotNull(course);
	}

	@Test
	public void createCourse() {
		assertEquals(courseName, course.getCourseName());
	}

	@Test
	public void getCourseById() {
		assertEquals(course, service.getCourse(courseId));
	}
	
	@Test
	public void getCourseByNumber() {
		assertEquals(course, service.getCourse(courseNum));
	}
	
	@Test
	public void getCoursesByTutor() {
		assertEquals(course, service.getCourses(tutor).get(0));
	}
	@Test
	public void getCourses() {
		assertEquals(courses, service.getAllCourses());
	}
	
	@Test
	public void getCourseBySession() {
		assertEquals(course, service.getCourse(session));
	}
	
	@Test
	public void getAllCourses() {
		assertEquals(courses, service.getAllCourses());
	}

	@Test
	public void queryCourseNotFound() {
		assertNull(service.getCourse(courseId+10));
	}

	@Test
	public void testCourseRemoval() {		
		assertNotNull(service.getCourse(courseId));
		service.removeCourse(courseId);
		assertNull(service.getCourse(courseId));
		try {
			service.removeCourse(noId);
			fail();
		}
		catch(Exception e) {
			assertEquals(e.getMessage(), "No such course exists.");
		}
	}
	

}
