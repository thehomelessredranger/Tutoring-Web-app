package ca.mcgill.ecse321.backend.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
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
public class RatePersistenceTests {
	
	@Mock
	private RateRepository rateDao;
	
	@InjectMocks
	private MasterService service;

	private static final int intrate = 2;
	private static final int rateId = 1;
	private Rate rate;
	List<Rate> expectedRate;

	@Before
	public void setMockOutput() {
		when(rateDao.findByRateId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(rateId)) {
				return rate;
			}
			return null;
		});
		when(rateDao.findRateByCourse(any())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(expectedRate)) {
				return rate;
			}
			return null;
		});
	}
	
	@Before
	public void setUpMocks() {
		
	}
	
	@Test
	public void querySessions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addReview() {
		fail("Not yet implemented");
	}

}