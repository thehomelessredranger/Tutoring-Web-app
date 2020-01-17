package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
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

import ca.mcgill.ecse321.backend.dao.RoleRepository;
import ca.mcgill.ecse321.backend.dao.UserRepository;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.MasterService;



@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;

	@InjectMocks
	private MasterService service;
	private static final int noId = 10;

	private static final int id1 = 0;
	private static final int id2 = 1;
	private static final int id3 = 2;
	private static final int id4 = 3;

	private static Student student;
	private static Tutor tutor;
	private static Tutor tutor2;
	private static Manager manager;
	

	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();

	private static final String name = "name";
	private static final String name2 = "name2";
	private static final String pass = "pass";
	private static final String pass2 = "pass2";


	@Before
	public void setMockOutput() {

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});
		when(roleDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(id1)) {
				return Optional.of(u1.getUserRole());
			} else if(invocation.getArgument(0).equals(id2)) {
				return Optional.of(u2.getUserRole());
			} else if(invocation.getArgument(0).equals(id3)) {
				return Optional.of(u3.getUserRole());
			} else if(invocation.getArgument(0).equals(id4)) {
				return Optional.of(u4.getUserRole());
			} else {
				return Optional.empty();
			}
		});

	}

	@Before
	public void setUpMocks() {
		allRoles.clear();
		allUsers.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u4 = service.createUser(id4, name, pass, "Manager");
		u2 = service.createUser(id2, name, pass, "Student");
		allRoles.add(u4.getUserRole());
		allRoles.add(u2.getUserRole());
		u1 = service.createUser(id1, name, pass, "Tutor");
		u3 = service.createUser(id3, name2, pass2, "Tutor");
		allUsers.add(u1);
		allUsers.add(u2);
		allUsers.add(u3);
		allUsers.add(u4);
		allRoles.add(u1.getUserRole());
		allRoles.add(u3.getUserRole());
		
		tutor = (Tutor) u1.getUserRole();
		student = (Student) u2.getUserRole();
		tutor2 = (Tutor) u3.getUserRole();
		manager = (Manager) u4.getUserRole();
	}


	@Test
	public void testRoleCreation() {
		assertNotNull(student);
		assertNotNull(tutor);
		assertNotNull(tutor2);
		assertNotNull(manager);
	}

	@Test
	public void createRole() {
		assertEquals(u1.getUserRole(), service.getRole(id1));
		assertEquals(u2.getUserRole(), service.getRole(id2));
		assertEquals(u3.getUserRole(), service.getRole(id3));
		assertEquals(u4.getUserRole(), service.getRole(id4));
		assertNull(service.getRole(noId));
	}
	
	@Test
	public void tutorNoManager() {
		allRoles.remove(manager);
		try {
			service.createUser(10, name, pass, "Tutor");
			fail("did not throw error");
		} catch (IllegalArgumentException e) {
			if (!e.getMessage().equals("No valid managers for the tutor.")) {
				fail("did not throw error");
			}
		}
	}
	
	@Test
	public void noRoleSelected() {
		try {
			service.createUser(10, name, pass, "role");
			fail("did not throw error");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "UserRole Incorrectly Specified");
		}
	}	
}