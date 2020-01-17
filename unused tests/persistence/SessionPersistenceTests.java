package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
public class SessionPersistenceTests {
	
	@Mock
	private SessionRepository sessionDao;
	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;
	
	private SingleSession session;
	private Tutor tutor;
	private Tutor tutor2;
	private User userTutor;
	private User userTutor2;
	private UserRole role;
	private User userStudent1;
	private User userStudent2;
	private static final int roomNum = 2;
	private static final int sessionDuration = 1;
	private static final Room room = Room.largeRoom;
//	private static final LocalDateTime date = new LocalDateTime(new LocalDate(), new LocalTime());
	private static final String time = "11:30";
	
	private static final int id1 = 1;
	private static final int id2 = 1;
	private static final int id3 = 1;
	
	
	private static final int noId = 0;
	private static final String name = "name";
	private static final String name2 = "name2";
	private static final String pass = "pass";
	private static final String type = "Tutor";
	
	private List<Session> tutorSession;
	private List<Session> courseSession;
	private List<Session> roomSession;
	private List<Session> dateSession;
	private static final int sessionId = 1; 
	private Course course;
	private static final int roomNumber = 1;
	private LocalDateTime date;
	
	@Before
	public void setMockOutput() {
		when(sessionDao.findSessionByTutor(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(tutor)) {
				return tutorSession;
			}
			return null;
		});
		when(sessionDao.findSessionByCourse(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(course)) {
				return courseSession;
			}
			return null;
		});
		when(sessionDao.findSessionBySessionId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(sessionId)) {
				return session;
			}
			return null;
		});
		when(sessionDao.findSessionByRoomNumber(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(roomNumber)) {
				return roomSession;
			}
			return null;
		});
		when(sessionDao.findSessionByDate(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(date)) {
				return dateSession;
			}
			return null;
		});

	}
	
	@Before
	public void setUpMocks() {
		
	}
	
	@Test
	public void setTutor() {
		session.setTutor(tutor2);
		assertEquals(tutor2, service.getSession(sessionId).getTutor());
	}
	
	@Test
	public void queryRoomNum() {
		assertEquals(roomNumber, service.getSession(sessionId).getRoomNumber());
	}

}