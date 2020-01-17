package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoursePersistenceTests {

	@InjectMocks
	private MasterService service;
	
	@Mock
	private CourseRepository courseDao;
	
	private Course course;
	private static final String courseName = "courseName";
	private static final int courseId = 1;
	
	private List<Course> tutorCourse;
	private List<Course> subjectCourse;
	private Session session;
	private static final String subject = "subject";
	private Tutor tutor;
	

	@Before
	public void setMockOutput() {
		when(courseDao.findByCourseName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseName)) {
				return course;
			}
			return null;
		});
		when(courseDao.findByCourseNumber(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseId)) {
				return course;
			}
			return null;
		});
		when(courseDao.findCourseByTutor(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(tutor)) {
				return tutorCourse;
			}
			return null;
		});
		when(courseDao.findCourseBySubject(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(subject)) {
				return subjectCourse;
			}
			return null;
		});
		when(courseDao.findCourseBySession(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(session)) {
				return course;
			}
			return null;
		});
	}
	
	@Before
	public void setUpMocks() {
		
	}
	
	@Test
	public void saveUser() {
		fail("Not yet implemented");
	}
	
	@Test
	public void queryUser() {
		fail("Not yet implemented");
	}

}