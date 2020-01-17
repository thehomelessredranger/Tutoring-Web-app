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
public class TutorPersistenceTests {

	@Mock
	private UserRepository userDao;
	@Mock
	private RoleRepository roleDao;
	
	@InjectMocks
	private MasterService service;

	@Before
	public void setMockOutput() {
		
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