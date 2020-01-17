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
public class RateTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private RateRepository rateDao;
	@Mock
	private CourseRepository courseDao;

	@InjectMocks
	private MasterService service;
	private static final int noId = 10;

	private static final int tutorId = 0;
	private static final int tutorId2 = 2;
	private static final int managerId = 3;
	private static final int rateVal = 3;


	private static Tutor tutor;
	private static Tutor tutor2;
	private static Rate rate;
	private static Course course;
	
	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();

	private static final String name = "name";
	private static final String pass = "pass";
	private static final String subject = "subject";
	private static final String courseName = "subject321";
	private static final int courseNum = 321;
	
	private static List<Rate> rates = new ArrayList<>();
	
	private static final int rateId = 30;


	@Before
	public void setMockOutput() {

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});


		when(rateDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return rates;
		});
		when(rateDao.findRateByCourse(any(Course.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Rate> courseRates = new ArrayList<>();
			for(Rate aRate: rates) {
				if(aRate.getCourse().equals(invocation.getArgument(0))) {
					courseRates.add(aRate);
				}
			}
			return courseRates;
		});
		when(rateDao.findRateByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Rate> tutorRates = new ArrayList<>();
			for(Rate aRate: rates) {
				if(aRate.getTutor().equals(invocation.getArgument(0))) {
					tutorRates.add(aRate);
				}
			}
			return tutorRates;
		});

		when(rateDao.findByRateId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(rateId)) {
				return rate;
			}
			return null;
		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(rateId)) {
				rate=null;
			} 
			return null;
		}).when(rateDao).deleteById(anyInt());
	}

	@Before
	public void setUpMocks() {
		allRoles.clear();
		allUsers.clear();
		rates.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u4 = service.createUser(managerId, name, pass, "Manager");
		allRoles.add(u4.getUserRole());
		
		u1 = service.createUser(tutorId, name, pass, "Tutor");
		u3 = service.createUser(tutorId2, name, pass, "Tutor");
		allUsers.add(u1);
		allUsers.add(u2);
		allUsers.add(u3);
		allUsers.add(u4);
		allRoles.add(u1.getUserRole());
		allRoles.add(u2.getUserRole());
		allRoles.add(u3.getUserRole());
		
		tutor = (Tutor) u1.getUserRole();
		tutor2 = (Tutor) u3.getUserRole();
		course = service.createCourse(subject, courseNum, courseName);
		service.addTutorToCourse(tutor, course);
		assertEquals(0, service.getAllRates().size());
		rate = service.createRate(rateVal, tutor, course);
		rate.setId(rateId);
		rates.add(rate);
		
	}


	@Test
	public void testRateCreation() {
		assertNotNull(rate);
	}


	@Test
	public void createRate() {
		assertEquals(rateVal, rate.getRate());
	}
	@Test
	public void overWriteRate() {
		int newRate = rateVal+1;
		service.createRate(newRate, tutor, course);
		assertEquals(newRate, rate.getRate());
	}

	@Test
	public void getRateById() {
		assertEquals(rate, service.getRate(rateId));
	}

	@Test
	public void getAverageRate() {
		Rate rate2 = service.createRate(rateVal, tutor2, course);	
		rates.add(rate2);
		assertEquals(rate, service.getAllRates().get(0));
		assertEquals(rate2, service.getAllRates().get(1));
	}

	@Test
	public void queryRateByCourse() {
		Rate a = service.getRates(course).get(0);
		assertEquals(rate, a);
	}
	
	@Test
	public void queryRateByTutor() {
		Rate a = service.getRates(tutor).get(0);
		assertEquals(rate, a);
	}

	@Test
	public void queryRateNotFound() {
		assertNull(service.getRate(rateId+10));
	}

	@Test
	public void testOverwriteRate() {
		int newRate = 321;
		service.createRate(newRate, tutor, course);
		assertEquals(newRate, rate.getRate());
	}
	@Test
	public void testChangeRate() {
		assertEquals(rate.getRate(), rateVal);
		int newRate = 321;
		service.changeRate(rateId, newRate);
		assertEquals(newRate, rate.getRate());
		
		try {
			service.changeRate(noId, newRate);
		} catch(Exception e) {
			assertEquals("No such rate exists.", e.getMessage());
		}
	}

	@Test
	public void testRateRemoval() {
		assertNotNull(service.getRate(rateId));
		service.removeRate(rateId);
		assertNull(service.getRate(rateId));
		try {
			service.removeRate(noId);
		}
		catch(Exception e) {
			assertEquals(e.getMessage(), "No such rate exists.");
		}
	}
}
