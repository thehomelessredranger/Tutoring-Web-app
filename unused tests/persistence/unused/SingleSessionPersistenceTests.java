package ca.mcgill.ecse321.backend.persistence.unused;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
public class SingleSessionPersistenceTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;
	
	private GroupSession session;
	private Tutor tutor;
	private User userTutor;
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
	
	

	@Test
	public void setTutor() {
		fail("Not yet implemented");
	}
	
	@Test
	public void queryUser() {
		fail("Not yet implemented");
	}

}