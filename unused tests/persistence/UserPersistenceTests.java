package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPersistenceTests {
	
	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;
	
	private User user;
	private UserRole role;
	private static final Integer id = 1;
	private static final int noId = 0;
	private static final String name1 = "name1";
	private static final String name2 = "name2";
	private static final String pass = "pass";
	private static final String type = "Tutor";
	
	private List<User> userRoleUsers;
	
	
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
		when(userDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(id)) {
				return user;
			}
			return null;
		});
		when(userDao.findUserByUserRole(any())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(role)) {
				return userRoleUsers;
			} 
			return null;
		});
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
		when(roleDao.findTutorByisVerified(anyBoolean())).thenAnswer((InvocationOnMock invocation) -> {
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

	
	@Before
	public void setUpMocks() {
		userDao.deleteAll();
		roleDao.deleteAll();
		user = mock(User.class);
		user = service.createUser(id, name1, pass, type);
		role = mock(Tutor.class);
		role = user.getUserRole();
	}

	@Test
	public void queryId() {
		User user = service.getUser(id);
//		User user = userDao.findById(id);
		assertEquals(name1, user.getName());
		assertNull(service.getUser(noId));
	}
	
	@Test
	public void saveName() {
		User user = service.getUser(id);
		user.setName(name2);
		assertEquals(name2, user.getName());
	}

}