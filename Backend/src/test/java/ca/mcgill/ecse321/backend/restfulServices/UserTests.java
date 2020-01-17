package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
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
import ca.mcgill.ecse321.backend.dao.RoleRepository;
import ca.mcgill.ecse321.backend.dao.UserRepository;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.MasterService;
import ca.mcgill.ecse321.backend.service.PasswordStorage;
import ca.mcgill.ecse321.backend.service.PasswordStorage.CannotPerformOperationException;
import ca.mcgill.ecse321.backend.service.PasswordStorage.InvalidHashException;



@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@InjectMocks
	private MasterService service;
	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;

	private static final int id1 = 0;
	private static final int id2 = 1;
	private static final int id3 = 2;
	private static final int id4 = 3;
	private static final int noId = 10;

	private static Student student;
	private static Tutor tutor;
	private static Tutor tutor2;
	private static Manager manager;
	

	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static List<User> allUsers = new ArrayList<>();
	private static Set<UserRole> allRoles = new HashSet<>();

	private static final String name = "name";
	private static final String name2 = "name2";
	private static final String pass = "pass";
	private static final String pass2 = "pass2";


	@Before
	public void setMockOutput() {
		when(userDao.findByIdNumber(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(id1)) {
				return u1;
			} else if(invocation.getArgument(0).equals(id2)) {
				return u2;
			} else if(invocation.getArgument(0).equals(id3)) {
				return u3;
			} else if(invocation.getArgument(0).equals(id4)) {
				return u4;
			} else {
				return null;
			}
		});
		when(userDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allUsers;
		});
		
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(id1)) {
				u1=null;
				tutor = null;
			} else if(invocation.getArgument(0).equals(id2)) {
				u2=null;
				student = null;
			} else if(invocation.getArgument(0).equals(id3)) {
				u3=null;
				tutor2 = null;
			} 
			return null;
		}).when(userDao).deleteById(anyInt());
		

		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
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
		
		student = (Student) u2.getUserRole();
		tutor = (Tutor) u1.getUserRole();
		tutor2 = (Tutor) u3.getUserRole();
		manager = (Manager) u4.getUserRole();
	}


	@Test
	public void testUserCreation() {
		assertNotNull(u1);
		assertNotNull(u2);
		assertNotNull(u3);
		assertNotNull(u4);
	}

	@Test
	public void createUsersTests() {
		assertEquals(name, service.getUser(id1).getName());
		assertEquals(name2, service.getUser(id3).getName());
		assertEquals(name, service.getUser(id2).getName());
		assertEquals(name, service.getUser(id4).getName());
	}
	
	@Test
	public void removeUserTests() {
		assertNotNull(service.getUser(id2));
		service.removeUser(id2);
		assertNull(service.getUser(id2));
		assertNull(u2);
		assertNull(student);
		assertNotNull(u1);
		
		try {
			service.removeUser(noId);
			fail("removed null");
		} catch(NullPointerException e) {
			assertEquals(e.getMessage(), "No such user exists.");
		}
		
	}

	@Test
	public void verifyPasswordsTests() {
		try {
			assertTrue(PasswordStorage.verifyPassword(pass, service.getUser(id1).getPassword()));
			assertTrue(PasswordStorage.verifyPassword(pass, service.getUser(id2).getPassword()));
			assertTrue(PasswordStorage.verifyPassword(pass2, service.getUser(id3).getPassword()));
			assertTrue(PasswordStorage.verifyPassword(pass, service.getUser(id4).getPassword()));
			System.out.println(service.getUser(id1).getPassword());
			System.out.println(service.getUser(id2).getPassword());
			System.out.println(service.getUser(id3).getPassword());
			System.out.println(service.getUser(id4).getPassword());
		} catch (CannotPerformOperationException e) {
			fail(e.getMessage());
		} catch (InvalidHashException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void loginTest() {
		assertNull(service.logIn(noId, pass));
		assertNull(service.logIn(id1, pass2));
		assertEquals(u1,service.logIn(id1, pass));
	}

	@Test
	public void authenticateUserTest() {
		try {
			assertTrue(service.authenticateUser(id1, pass));
			assertTrue(service.authenticateUser(id2, pass));
			assertTrue(service.authenticateUser(id3, pass2));
			assertTrue(service.authenticateUser(id4, pass));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(service.authenticateUser(noId, pass));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Username or Password incorrect, please try again.");
		}
		try {
			assertTrue(service.authenticateUser(id1, pass2));
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Username or Password incorrect, please try again.");
		}
	}
	
	@Test
	public void getAllUsersTest() {
		assertEquals(allUsers, service.getAllUsers());
	}
	
	@Test
	public void getUserTests() {
		assertEquals(u1, service.getUser(id1));
		assertNull(service.getUser(noId));
	}
	
	
}