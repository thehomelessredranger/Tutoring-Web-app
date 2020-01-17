package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
public class UserRolePersistenceTests {

	private UserRepository userDao;
	private RoleRepository roleDao;

	@InjectMocks
	private MasterService service;

	private User user;
	private UserRole role;
	private static final int idNumber = 1;

	private List<Tutor> courseTutor;
	private List<Tutor> managerTutor;
	private List<Tutor> verifiedTutor;
	private Course course;
	private Manager manager;
	private static final boolean verified = true;
	private Session session;
	private Tutor tutor;
	private Rate rate;
	private Student student;
	private List<Student> sessionStudents;


	@Before
	public void setMockOutput() {
		when(roleDao.findByUser(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(user)) {
				return role;
			}
			return null;
		});
		when(roleDao.findByUserIdNumber(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(idNumber)) {
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
		when(roleDao.findTutorByisVerified(any())).thenAnswer((InvocationOnMock invocation) -> {
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
