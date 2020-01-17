package ca.mcgill.ecse321.backend.restfulServices;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CourseTests.class, RateTests.class, ReviewTests.class,
//	ReviewPersistenceTests.class, 
	UserTests.class, RoleTests.class, AvailabilityTests.class, SessionTests.class, QualificationsTests.class })
public class AllTests {

}
