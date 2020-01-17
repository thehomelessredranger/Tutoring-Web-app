package ca.mcgill.ecse321.backend.persistence.unused;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerPersistenceTests {
	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;

	private User user1;
	private UserRole role;
	private static final int id1 = 1;
	private static final int noId = 0;
	private static final String name1 = "name1";
	private static final String name2 = "name2";
	private static final String pass = "pass";
	private static final String type = "Manager";
	
	
	@Before
	public void setMockOutput() {
		
	}
	
	@Before
	public void setUpMocks() {
		user1 = mock(User.class);
		user1 = service.createUser(id1, name1, pass, type);
		role = mock(Manager.class);
//		role = roleService.createTutor();
		role = user1.getUserRole();
	}

	@Test
	public void queryTutors() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addSessions() {
		fail("Not yet implemented");
	}

}