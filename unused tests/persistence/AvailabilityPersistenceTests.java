package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
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
public class AvailabilityPersistenceTests {

	@Mock
	private AvailabilityRepository availDao;
	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;
	
	private Availability avail;
	private Tutor tutor;
	private User user;
	private UserRole role;
	private static final int startTime = 8;
	private static final int endTime = 12;
	private static final int endTime2 = 14;
	private static final Day dayOfWeek = Day.MONDAY;
	private static final int id = 1;
	private static final int noId = 0;
	private static final String name = "name";
	private static final String pass = "pass";
	private static final String type = "Tutor";
	private static final int availId = 1;
	private List<Tutor> tutorAvail;
	
	@Before
	public void setMockOutput() {
		when(availDao.findAvailabilityByTutor(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(tutor)) {
				return tutorAvail;
			}
			return null;
		});
		when(availDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(availId)) {
				return avail;
			} else {
				return null;
			}
		});
	}
	
	@Before
	public void setUpMocks() {
		user = mock(User.class);
		user = service.createUser(id, name, pass, "Tutor");
		tutor = mock(Tutor.class);
		tutor = (Tutor) user.getUserRole();
		avail = mock(Availability.class);
		//avail = service.createAvailability();
	}

	@Test
	public void queryStart() {
		//assertEqual(startTime, service.getAvailabilityById(id).getStartTime());
		//assertNull(service.findById(noId));
	}
	
	@Test
	public void setEnd() {
		//TODO
		//Availability availability = service.findAvailabilityById(id);
		//availability.setEndTime(endTime2);
		//assertEqual(endTime2, availability.getEndTime());
	}

}
