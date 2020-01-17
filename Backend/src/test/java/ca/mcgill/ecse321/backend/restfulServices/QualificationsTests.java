package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

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
public class QualificationsTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private QualificationRepository qualDao;

	@InjectMocks
	private MasterService service;

	private static final int id1 = 0;
//	private static final int id2 = 1;
//	private static final int id3 = 2;
//	private static int availabilityId = 1;
//	private static int availabilityId2 = 2;
//	private static final int noId = 10;

	private static Tutor tutor;
//	private static Availability availability;
//	private static Availability availability2;
	private static Tutor tutor2;
	




//	private static User u1;
//	private static User u2;
//	private static User u3;
//	private static Set<User> allUsers = new HashSet<>();
//	private static Set<UserRole> allRoles = new HashSet<>();
//	private static List<Availability> allA = new ArrayList<>();
	private static Qualification qual;
	private static Set<Qualification> quals = new HashSet<>();


	private static final String school = "face";
	private static final String degree = "dec";
	private static final int startYear = 2012;
	private static final int endYear = 2016;
	private static final boolean onGoing = false;


	@Before
	public void setMockOutput() {

		when(qualDao.findQualificationByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(tutor)) {
				return quals;
			}
			return new HashSet<>();
		});

//		when(availDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
//			return allA;
//		});
//		when(availDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
//			if (invocation.getArgument(0).equals(availabilityId)) {
//				return availability;
//			}
//			else if (invocation.getArgument(0).equals(availabilityId2)) {
//				return availability2;
//			}
//			return null;
//		});
//		when(availDao.findAvailabilityByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
//			List<Availability> aList = new ArrayList<>();
//			for(Availability a:allA) {
//				if (a.getTutor().equals(invocation.getArgument(0))) {
//					aList.add(a);
//				}
//			}
//			return aList;
//		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(qual)) {
				quals.remove(qual);
				qual=null;
			}
			return null;
		}).when(qualDao).delete(any(Qualification.class));

	}

	@Before
	public void setUpMocks() {
		quals.clear();
		tutor = new Tutor();
		qual = service.createQualification(tutor, school, degree, startYear, endYear, onGoing);
		quals.add(qual);
		
	}


	@Test
	public void testQualificationCreation() {
		assertNotNull(qual);
	}

	@Test
	public void createQualificationTests() {
		assertEquals(school, qual.getSchool());
		assertEquals(degree, qual.getDegree());
		assertEquals(startYear, qual.getStartYear());
		assertEquals(endYear, qual.getEndYear());
		assertEquals(onGoing, qual.isOnGoing());
		for(Qualification q:tutor.getQualification()) {
			assertTrue(quals.contains(q));
		}
		Qualification q2 = service.createQualification(tutor, school, degree, startYear, endYear, onGoing);
		quals.add(q2);
		for(Qualification q:tutor.getQualification()) {
			assertTrue(quals.contains(q));
		}
	}

	@Test
	public void getQualificationByTutor() {
		assertEquals(service.getQualification(tutor), quals);
		Tutor tutor2 = mock(Tutor.class);
		assertEquals(new HashSet<Qualification>(), service.getQualification(tutor2));
	}



	@Test
	public void removeQualificationTests() {
		assertNotNull(qual);
		Tutor tutor2 = mock(Tutor.class);
		try {
			service.removeQualification(tutor2, qual);
			fail("deleted null");
		} catch (Exception e) {
			assertEquals("No qualifications available.", e.getMessage());
		}
		service.removeQualification(tutor, qual);
		assertNull(qual);
		assertTrue(service.getQualification(tutor).isEmpty());
	}
}
