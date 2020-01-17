package ca.mcgill.ecse321.backend.restfulServices;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.backend.dao.CourseRepository;
import ca.mcgill.ecse321.backend.dao.RoleRepository;
import ca.mcgill.ecse321.backend.dao.RoomRepository;
import ca.mcgill.ecse321.backend.dao.SessionRepository;
import ca.mcgill.ecse321.backend.dao.UserRepository;
import ca.mcgill.ecse321.backend.model.Course;
import ca.mcgill.ecse321.backend.model.GroupSession;
import ca.mcgill.ecse321.backend.model.Manager;
import ca.mcgill.ecse321.backend.model.Room;
import ca.mcgill.ecse321.backend.model.RoomBig;
import ca.mcgill.ecse321.backend.model.RoomSmall;
import ca.mcgill.ecse321.backend.model.Session;
import ca.mcgill.ecse321.backend.model.SingleSession;
import ca.mcgill.ecse321.backend.model.Student;
import ca.mcgill.ecse321.backend.model.Tutor;
import ca.mcgill.ecse321.backend.model.User;
import ca.mcgill.ecse321.backend.model.UserRole;
import ca.mcgill.ecse321.backend.service.MasterService;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	@Mock
	private CourseRepository courseDao;
	@Mock
	private SessionRepository sessionDao;
	@Mock
	private RoomRepository roomDao;

	@InjectMocks
	private MasterService service;

	private static final int tutorId = 0;
	private static final int studentId = 1;
	private static final int tutorId2 = 2;
	private static final int managerId = 3;
	private static final int noId = 10;
	
	private static final String subject = "subject";
	private static final String courseName = "subject321";
	private static final int courseNum = 321;
	
	private static final int roomNum = 3;

	private static final int sessionId = 123;
	
	private static final int duration = 30;


	private static Student student;
	private static Tutor tutor;
	private static Student student2;
	private static Course course;
	private static Session session;
	private static List<Session> sessions = new ArrayList<>();
	private static RoomBig roomL;
	private static RoomSmall roomS;

	
	

	private static User u1;
	private static User u2;
	private static User u3;
	private static User u4;
	private static Set<User> allUsers = new HashSet<>();
	private static Set<UserRole> allRoles = new HashSet<>();
	private static List<Student> sessionStudents = new ArrayList<>();
	private static Set<Room> allRooms = new HashSet<>();

	private static final String name = "name";
	private static final String pass = "pass";


	@Before
	public void setMockOutput() {
		when(roleDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRoles;
		});

		when(sessionDao.findSessionBySessionId(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			for(Session s:sessions) {
				if(invocation.getArgument(0).equals(s.getId())) {
					return s;
				}
			}
			return null;
		});
		when(sessionDao.findSessionByRoom(any(Room.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Session> aList = new ArrayList<>();
			for(Session s:sessions) {
				if (invocation.getArgument(0).equals(s.getRoom())) {
					aList.add(s);
				}
			}
			return aList;
		});
		when(sessionDao.findSessionByCourse(any(Course.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Session> aList = new ArrayList<>();
			for(Session s:sessions) {
				if (s.getCourse().equals(invocation.getArgument(0))) {
					aList.add(s);
				}
			}
			return aList;
		});
		when(sessionDao.findSessionByTutor(any(Tutor.class))).thenAnswer( (InvocationOnMock invocation) -> {
			List<Session> aList = new ArrayList<>();
			for(Session s:sessions) {
				if (s.getTutor().equals(invocation.getArgument(0))) {
					aList.add(s);
				}
			}
			return aList;
		});
		when(sessionDao.findSessionByTutorAndSessionAccepted(any(Tutor.class), anyBoolean())).thenAnswer( (InvocationOnMock invocation) -> {
			List<Session> aList = new ArrayList<>();
			for(Session s:sessions) {
				if (s.getTutor().equals(invocation.getArgument(0))) {
					aList.add(s);
				}
			}
			List<Session> bList = new ArrayList<>();
			for(Session s:aList) {
				if (invocation.getArgument(1).equals(s.isSessionAccepted())) {
					bList.add(s);
				}
			}
			return bList;
		});
		when(sessionDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return sessions;
		});
		doAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(sessionId)) {
				sessions.remove(session);
				session=null;
			} 
			return null;
		}).when(sessionDao).deleteById(anyInt());
		when(roomDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			return allRooms;
		});
	}

	@Before
	public void setUpMocks() {
		allRoles.clear();
		allUsers.clear();
		allRooms.clear();
		sessions.clear();
		u1 = mock(User.class);
		u2 = mock(User.class);
		u3 = mock(User.class);
		u4 = mock(User.class);
		session = mock(Session.class);
//		roomS = mock(RoomSmall.class);
		roomS = new RoomSmall();
		roomL = mock(RoomBig.class);
		allRooms.add(roomS);
		allRooms.add(roomL);
		
		u4 = service.createUser(managerId, name, pass, "Manager");
		allRoles.add(u4.getUserRole());
		
		u1 = service.createUser(tutorId, name, pass, "Tutor");
		u2 = service.createUser(studentId, name, pass, "Student");
		u3 = service.createUser(tutorId2, name, pass, "Student");
		allUsers.add(u1);
		allUsers.add(u2);
		allUsers.add(u3);
		allUsers.add(u4);
		allRoles.add(u1.getUserRole());
		allRoles.add(u2.getUserRole());
		allRoles.add(u3.getUserRole());
		
		student = (Student) u2.getUserRole();
		tutor = (Tutor) u1.getUserRole();
		student2 = (Student) u3.getUserRole();

		course = mock(Course.class);
		course = service.createCourse(subject, courseNum, courseName);
		service.addTutorToCourse(tutor, course);
		sessionStudents.clear();
		sessionStudents.add(student);
		
		
		session = service.createSession(course, tutor, student, duration, -1, -1, -1, -1, -1);
		session.setId(sessionId);
		
		sessions.add(session);
		
	}


	@Test
	public void testSessionCreation() {
		assertNotNull(session);
	}

	@Test
	public void createSession() {
		assertEquals(duration, session.getSessionDuration());
		if(!(session instanceof SingleSession)) {
			fail("not right type of session created (should be SingleSession)");
		}
		sessionStudents.add(student2);
		session = service.createSession((Manager)u4.getUserRole(), course, tutor, sessionStudents, roomL, duration, -1, -1, -1, -1, -1);
		if(!(session instanceof GroupSession)) {
			fail("not right type of session created (should be GroupSession)");
		}
		try {
			service.createSession((Manager)u4.getUserRole(), course, tutor, sessionStudents, roomS, duration, -1, -1, -1, -1, -1);
		} catch(Exception e) {
			assertEquals("Room too small for group session.", e.getMessage());
		}
		sessionStudents.clear();
		try {
			session = service.createSession((Manager)u4.getUserRole(), course, tutor, sessionStudents, roomL, duration, -1, -1, -1, -1, -1);
			fail("did not catch empty list error");
		} catch(IllegalArgumentException e) {
			assertEquals(e.getMessage(),"Not Enough Students for a session");
		}
	}

	@Test
	public void getSessionById() {
		assertEquals(session, service.getSession(sessionId));
	}
	
	@Test
	public void acceptSession() {
		assertTrue(session.isSessionAccepted()!=true);
		service.acceptSession(sessionId);
		assertTrue(session.isSessionAccepted());
	}
	
	@Test
	public void refuseSession() {
		service.refuseSession(sessionId);
		assertFalse(session.isSessionAccepted());
	}
	
	@Test
	public void getSessionsByCourse() {
		assertEquals(session, service.getSessions(course).get(0));
	}
	@Test
	public void getSessionsByTutor() {
		assertEquals(session, service.getSessions(tutor).get(0));
	}
	@Test
	public void getSessionsByRoom() {
		assertEquals(0, service.getSessions(roomS).size());
		service.acceptSession(session.getId());
		assertEquals(session, service.getSessions(roomS).get(0));
	}
	
	@Test
	public void overBook() {
		Session session2 = service.createSession(course, tutor, student, duration, -1, -1, -1, -1, -1);
		session2.setId(sessionId+1);
		sessions.add(session2);
		service.acceptSession(session.getId());
		try {
			service.acceptSession(session2.getId());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("There are no empty rooms at this date and time", e.getMessage());
		}
		
	}
	
	@Test
	public void getAllSessions() {
		assertEquals(sessions, service.getAllSessions());
	}
	
	@Test
	public void getPendingSessions() {
		assertEquals(sessions, service.getPendingSessions(tutor));
	}

	@Test
	public void testSessionRemoval() {
		assertNotNull(service.getSession(sessionId));
		service.removeSession(sessionId);
		assertNull(service.getSession(sessionId));
		try {
			service.removeSession(noId);
			fail();
		}
		catch(Exception e) {
			assertEquals(e.getMessage(),"No such session exists");
		}
	}

}
