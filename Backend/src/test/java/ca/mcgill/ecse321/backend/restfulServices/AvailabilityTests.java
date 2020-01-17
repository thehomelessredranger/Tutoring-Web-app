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
public class AvailabilityTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private AvailabilityRepository availDao;

	@InjectMocks
	private MasterService service;

	private static final int id1 = 0;
	private static final int id2 = 1;
	private static final int id3 = 2;
	private static int availabilityId = 1;
	private static int availabilityId2 = 2;
	private static final int noId = 10;

	private static Tutor tutor;
	private static Availability availability;
	private static Availability availability2;
	private static Tutor tutor2;
	
	
	

	private static User u1;
	private static User u2;
	private static User u3;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();
	private static List<Availability> allA = new ArrayList<>();


	private static final String name = "name";
	private static final String pass = "pass";

	private static final int startTime = 1;
	private static final int endTime = 3;
	private static final Day day = Day.FRIDAY;

	@Before
	public void setMockOutput() {

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});

		when(availDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allA;
		});
		when(availDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(availabilityId)) {
				return availability;
			}
			else if (invocation.getArgument(0).equals(availabilityId2)) {
				return availability2;
			}
			return null;
		});
		when(availDao.findAvailabilityByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Availability> aList = new ArrayList<>();
			for(Availability a:allA) {
				if (a.getTutor().equals(invocation.getArgument(0))) {
					aList.add(a);
				}
			}
			return aList;
		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(availabilityId)) {
				availability=null;
			} 
			return null;
		}).when(availDao).deleteById(anyInt());

	}

	@Before
	public void setUpMocks() {
		allRoles.clear();
		allUsers.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u2 = service.createUser(id2, name, pass, "Manager");
		allRoles.add(u2.getUserRole());
		u1 = service.createUser(id1, name, pass, "Tutor");
		u3 = service.createUser(id3, name, pass, "Tutor");
		allUsers.add(u1);
		allUsers.add(u2);
		allUsers.add(u3);
		allRoles.add(u1.getUserRole());
		allRoles.add(u3.getUserRole());
		
		tutor = (Tutor) u1.getUserRole();
		tutor2 = (Tutor) u3.getUserRole();
		
		
		availability = service.createAvailability(tutor, startTime, endTime, day);
		availability.setId(availabilityId);
		availability2 = service.createAvailability(tutor2, startTime, endTime, day);
		availability2.setId(availabilityId2);
		allA.add(availability);
		allA.add(availability2);
	}


	@Test
	public void testAvailabilityCreation() {
		assertNotNull(availability);
	}

	@Test
	public void createAvailabilityTests() {
		assertEquals(day, availability.getDayOfWeek());
		assertEquals(startTime, availability.getStartTime());
		assertEquals(endTime, availability.getEndTime());
	}
	
	

	
	@Test
	public void getAllAvailabilityTests() {
		assertEquals(allA, service.getAllAvailabilities());
	}
	
	
	
	@Test
	public void getAvailabilityTests() {
		assertEquals(availability, service.getAvailability(availabilityId));
		assertNull(service.getAvailability(noId));
	}
	@Test
	public void removeAvailabilityTests() {
		assertNotNull(service.getAvailability(availabilityId));
		service.removeAvailability(availabilityId);
		assertNull(service.getAvailability(availabilityId));
		assertNull(availability);
		
		try {
			service.removeAvailability(noId);
			fail("removed null");
		} catch(NullPointerException e) {
			assertEquals(e.getMessage(), "No such availability exists.");
		}
		
	}
	@Test
	public void getTutorAvailabilityTests() {		
		assertEquals(availability, service.getAvailabilities(tutor).get(0));
		assertEquals(availability2, service.getAvailabilities(tutor2).get(0));
		User u = service.createUser(noId, name, pass, "Tutor");
		List<Availability> emptyList = new ArrayList<>();
		assertEquals(service.getAvailabilities((Tutor) u.getUserRole()), emptyList);
	}
}