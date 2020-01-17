package ca.mcgill.ecse321.backend.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.backend.dao.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.PasswordStorage.CannotPerformOperationException;
import ca.mcgill.ecse321.backend.service.PasswordStorage.InvalidHashException;

@Service
public class MasterService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AvailabilityRepository availabilityRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	RateRepository rateRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	RoomRepository roomRepository;

	/*
	 * Implementation for qualification
	 * @author The Elk
	 * */
	@Autowired
	QualificationRepository qualificationRepository;
	
	private static Random ran = new Random();

	/*
	 * Implementation for qualification
	 * @author The Elk
	 * */

	/**
	 * create a new qualification for tutor
	 * @param tutor that has qualifications
	 * @param school they went to
	 * @param degree they have
	 * @param startYear year they started their degree
	 * @param endYear year they end/ended their degree
	 * @param onGoing true if they are still pursuing their degree
	 * @return the newly created Qualification
	 */
	@Transactional
	public Qualification createQualification(Tutor tutor, String school, String degree, int startYear, int endYear,  Boolean onGoing) {

		Qualification q = new Qualification();
		Set <Qualification> qSet = tutor.getQualification();
		if (qSet == null) {
			qSet = new HashSet<>();
		}


		q.setSchool(school);
		q.setDegree(degree);
		q.setStartYear(startYear);
		q.setEndYear(endYear);
		q.setOnGoing(onGoing);

		qSet.add(q);

		tutor.setQualification(qSet);

		qualificationRepository.save(q);

		return q;

	}

	/*
	 * Implementation for qualification
	 * @author The Elk
	 * */

	/**
	 * get the set of qualifications of a tutor
	 * @param tutor that has qualifications
	 * @return set of qualification objects
	 */
	@Transactional
	public Set<Qualification> getQualification(Tutor tutor) {
		Set<Qualification> q = qualificationRepository.findQualificationByTutor(tutor);
		return q;
	}

	/*
	 * Implementation for qualification
	 * @author The Elk
	 * */

	/**
	 * remove a specific qualification from a tutor
	 * @param tutor that has a qualification
	 * @param qualitification specific qualification to remove from the tutor
	 */
	@Transactional
	public void removeQualification(Tutor tutor, Qualification qualitification) {
		Set <Qualification> qSet = tutor.getQualification();

		if(qSet == null) {
			throw new NullPointerException("No qualifications available.");
		}
		if(qSet.size()==0) {
			throw new IllegalArgumentException("No qualifications available.");
		}
		else {
			for (Qualification q : qSet) {
				if(q.equals(qualitification)) {
					qSet.remove(q);
					qualificationRepository.delete(q);
				}
			}
		}
	}

	/**
	 * remove a user given their id number
	 * @param ID number of user
	 */
	@Transactional
	public void removeUser(int ID) {
	  if (userRepository.findByIdNumber(ID) == null) {
	    throw new NullPointerException("No such user exists.");
	  }
	  roleRepository.deleteById(ID);
	  userRepository.deleteById(ID);
	}

	/**
	 * create a new user. randomizes their id
	 * @param name username of user
	 * @param password user's password
	 * @param Type of user. "Tutor", "Student", "Manager" are valid types
	 * @return newly created User
	 */
	@Transactional
	public User createUser(String name, String password, String Type) {
		Integer ID;
		do {
			ID = ran.nextInt();
		} while(userRepository.existsById(ID));
		return createUser(ID, name, password, Type);
	}
	
	/**
	 * create a new user with a specific id
	 * @param ID number of new user
	 * @param name username of the user
	 * @param password user's password
	 * @param Type of user. "Tutor", "Student", "Manager" are valid types
	 * @return newly created User
	 */
	@Transactional
	public User createUser(Integer ID, String name, String password, String Type) {
		User u = new User();
		if(userRepository.findUserByName(name)!=null) {
			throw new IllegalArgumentException("Sorry, this username has already been taken.");
		}
		u.setName(name);
		u.setId(ID);
		try {
			u.setPassword(PasswordStorage.createHash(password));
		} catch(PasswordStorage.CannotPerformOperationException e) {
			throw new IllegalArgumentException(e.getMessage());
		}


		UserRole role;

		if(Type.equals("Student")){
			Student aRole = new Student();
			aRole.getReview(); //initialize sets
			aRole.getSession();
			aRole.setAverageRating(Rating.NO_RATING);
			role = aRole;
		}else if(Type.equals("Tutor")) {
			Tutor aRole = new Tutor();
			List<Manager> managers = new ArrayList<>();
			for(UserRole r:roleRepository.findAll()) {
				if (r instanceof Manager) {
					managers.add((Manager)r);
				}
			}
			if (managers.size()==0) {
				throw new IllegalArgumentException("No valid managers for the tutor.");
			}
			int index = ran.nextInt(managers.size());
			Manager man = managers.get(index);
			aRole.setManager(man);
			aRole.getAvailability();
			aRole.getCourse();
			aRole.getRate();
			aRole.getReview();
			aRole.getSession();
			role = aRole;
		}
		else if(Type.equals("Manager")) {
			Manager aRole = new Manager();
			aRole.getSession();
			aRole.getTutor();
			role = aRole;
		}else {
			throw new IllegalArgumentException ("UserRole Incorrectly Specified");
		}
		role.setUser(u);
		role.setId(ID);
		u.setUserRole(role);
		userRepository.save(u);
		roleRepository.save(role);
		return u;
	}

	/**
	 * change a user's password
	 * @param id number of user
	 * @param password new password string of user
	 * @return user that changed their password
	 */
	@Transactional
	public User changePassword(int id, String password) {
		User u = getUser(id);
		String hash = u.getPassword();
		try {
			hash = PasswordStorage.createHash(password);
		} catch (CannotPerformOperationException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		u.setPassword(hash);
		
		return u;
	}
	
	/**
	 * get a specific user
	 * @param ID of user that you're searching for
	 * @return user object
	 */
	@Transactional
	public User getUser(Integer ID) {
		User u = userRepository.findByIdNumber(ID);
		return u;
	}

	/**
	 * get list of all users
	 * @return list of type User that contains all users in the system
	 */
	@Transactional
	public List<User> getAllUsers() {
		return toList(userRepository.findAll());
	}

	/**
	 * return a user if id and password are valid matches
	 * @param ID of user
	 * @param Password of user
	 * @return user that was logged into
	 */
	@Transactional
	public User logIn(int ID, String Password) {
		try {
			boolean authentic = authenticateUser(ID, Password);
			if (authentic) {
				User u = userRepository.findByIdNumber(ID);
				return u;
			}
			return null;
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	/**
	 * return a user if username and password are valid matches
	 * @param username of user
	 * @param password of user
	 * @return user that was logged into
	 */
	@Transactional
	public User logIn(String username, String password) {
		User aUser = userRepository.findUserByName(username);
		if(aUser == null) { 
			throw new IllegalArgumentException("Username or Password incorrect, please try again.");
		}
		return logIn(aUser.getId(), password);
	}

	/**
	 * authenticate if an id and password are a match
	 * @param ID number of user
	 * @param Password of user
	 * @return true if a match
	 * @throws IllegalArgumentException if incorrect username or password, or stored password is corrupted
	 */
	@Transactional
	public boolean authenticateUser(int ID, String Password) throws IllegalArgumentException{	//Verify log in credentials
		if(userRepository.findByIdNumber(ID)==null) { 
			throw new IllegalArgumentException("Username or Password incorrect, please try again.");
		}
		else {
			User u = getUser(ID);
			String storedPassword = u.getPassword();

			try {
				if (PasswordStorage.verifyPassword(Password,storedPassword)) {
					return true;
				}
				else {
					throw new IllegalArgumentException("Username or Password incorrect, please try again.");
				}
			} catch (CannotPerformOperationException | InvalidHashException e) {
				throw new IllegalArgumentException("Stored password is corrupted, please contact an Admin");
			}
		}
	}
	/**
	 * authenticate if an id and password are a match
	 * @param username of user
	 * @param password of user
	 * @return true if a match
	 * @throws IllegalArgumentException if incorrect username or password, or stored password is corrupted
	 */
	@Transactional
	public boolean authenticateUser(String username, String password) throws IllegalArgumentException{	//Verify log in credentials
		User aUser = userRepository.findUserByName(username);
		if(aUser==null) { 
			throw new IllegalArgumentException("Username or Password incorrect, please try again.");
		}
		return authenticateUser(aUser.getId(), password);
	}

	/**
	 * remove an availability given its id
	 * @param ID of availability
	 */
	@Transactional
	public void removeAvailability(int ID) {
	  if (availabilityRepository.findById(ID) == null) {
	    throw new NullPointerException("No such availability exists.");
	  }

	  availabilityRepository.deleteById(ID);
	}

	/**
	 * create an availability for a tutor
	 * @param tutor that creates the availability
	 * @param startTime of availability
	 * @param endTime of availability
	 * @param day of week of availability
	 * @return newly created availability
	 */
	@Transactional
	public Availability createAvailability(Tutor tutor, int startTime, int endTime, Day day) {
		Availability u = new Availability();
		u.setTutor(tutor);
		u.setStartTime(startTime);
		u.setEndTime(endTime);
		u.setDayOfWeek(day);

		availabilityRepository.save(u);

		return u;
	}

	/**
	 * get all availabilities of a tutor
	 * @param tutor that has availabilities
	 * @return list of availabilities that the tutor has
	 */
	@Transactional
	public List<Availability> getAvailabilities(Tutor tutor) {
		List<Availability> u = availabilityRepository.findAvailabilityByTutor(tutor);
		return u;
	}

	/**
	 * get specific availability
	 * @param id of availability
	 * @return chosen availability
	 */
	@Transactional
	public Availability getAvailability(int id) {
		Availability u = availabilityRepository.findById(id);
		return u;
	}

	/**
	 * get all availabilities
	 * @return list of availabilities 
	 */
	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilityRepository.findAll());
	}

	/**
	 * remove a specific course
	 * @param ID of course to remove
	 */
	@Transactional
	public void removeCourse(String ID) {
	  if (courseRepository.findByCourseId(ID) == null) {
	    throw new NullPointerException("No such course exists.");
	  }

	  courseRepository.deleteById(ID);
	}

	/**
	 * create a new course
	 * @param subject of course (EG ECSE)
	 * @param number of course (EG 321)
	 * @param name of course/short description (EG intro to software engineering)
	 * @return newly created course
	 */
	@Transactional
	public Course createCourse(String subject, int number, String name) {
		Course u = new Course();
		u.setSubject(subject);
		u.setCourseNumber(number);
		u.setCourseName(name);

		courseRepository.save(u);

		return u;
	}
	
	/**
	 * add a tutor to a course
	 * @param tutor to add to course
	 * @param course to add tutor to
	 */
	@Transactional
	public void addTutorToCourse(Tutor tutor, Course course) {
		course.getTutor().add(tutor);
		tutor.getCourse().add(course);
	}
	
	/**
	 * removes a tutor from a course
	 * @param tutor to remove from course
	 * @param course to remove tutor from
	 */
	@Transactional
	public void removeTutorFromCourse(Tutor tutor, Course course) {
		if (course.getTutor().contains(tutor)) {
			course.getTutor().remove(tutor);
		}
		if (tutor.getCourse().contains(course)) {
			tutor.getCourse().add(course);
		}
	}
	
	/**
	 * get all courses of a tutor 
	 * @param tutor that teaches courses
	 * @return list of courses the tutor teaches
	 */
	@Transactional
	public List<Course> getCourses(Tutor tutor) {
		List<Course> u = courseRepository.findCourseByTutor(tutor);
		return u;
	}


	/**
	 * get a specific course given its course number
	 * @param Number of course
	 * @return course that was searched for
	 */
	@Transactional
	public Course getCourse(int Number) {
		Course u = courseRepository.findByCourseNumber(Number);
		return u;
	}

	/**
	 * course that a session is a type of
	 * @param session that has a course
	 * @return course that has the given session
	 */
	@Transactional
	public Course getCourse(Session session) {
		Course u = courseRepository.findCourseBySession(session);
		return u;
	}

	/**
	 * gets a specific course from its id
	 * @param id of the course (made from subject+number)
	 * @return specific course
	 */
	@Transactional
	public Course getCourse(String id) {
		Course u = courseRepository.findByCourseId(id);
		return u;
	}


	/**
	 * gets all courses in the system
	 * @return list of all courses
	 */
	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}


	/**
	 * create a new rate
	 * @param rate of the tutor
	 * @param tutor that creates the rate
	 * @param course that the tutor creates the rate for
	 * @return newly created rate
	 */
	@Transactional
	public Rate createRate(int rate, Tutor tutor, Course course) {

		// Ensures no duplicate rate is created
		for (Rate r : getRates(tutor)) {
			if (r.getCourse().equals(course)) {
				r.setRate(rate);
				return r;
			}
		}

		// Create new rate if it doesn't already exist
		Rate u = new Rate();
		u.setTutor(tutor);
		u.setRate(rate);
		u.setCourse(course);

		rateRepository.save(u);

		return u;
	}

	/**
	 * remove a rate given its id
	 * @param ID of rate to remove
	 */
	@Transactional
	public void removeRate(int ID) {
	  if (rateRepository.findByRateId(ID) == null) {
	    throw new NullPointerException("No such rate exists.");
	  }

	  rateRepository.deleteById(ID);
	}
	
	/**
	 * change a rate given its id and its new value
	 * @param ID of rate to change
	 * @param rate of new Rate
	 */
	@Transactional
	public void changeRate(int ID, int rate) {
	  if (rateRepository.findByRateId(ID) == null) {
	    throw new NullPointerException("No such rate exists.");
	  }

	  Rate r = rateRepository.findByRateId(ID);
	  r.setRate(rate);
	  rateRepository.save(r);
	}

	/**
	 * get all rates of a tutor
	 * @param tutor that has rates
	 * @return list of rates for a tutor
	 */
	@Transactional
	public List<Rate> getRates(Tutor tutor) {
		List<Rate> u = rateRepository.findRateByTutor(tutor);
		return u;
	}

	/**
	 * get all rates of a course
	 * @param course that has rates
	 * @return list of rates that a course has
	 */
	@Transactional
	public List<Rate> getRates(Course course) {
		List<Rate> u = rateRepository.findRateByCourse(course);
		return u;
	}
	
	/**
	 * gets the average rate of a course
	 * @param course that's being checked
	 * @return average of rates
	 */
	@Transactional
	public double getAverageRate(Course course) {
		List<Rate> u = getRates(course);
		double ave=0;
		for(Rate r:u) {
			ave+=r.getRate();
		}
		ave/=u.size();
		
		return ave;
	}

	/**
	 * gets the rate of a course/tutor pair
	 * @param course of the rate
	 * @param tutor of the rate
	 * @return unique rate that has the given course and tutor
	 */
	@Transactional
	public Rate getRate(Course course, Tutor tutor) {
		for (Rate r : getRates(tutor)) {
			if (r.getCourse().equals(course)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * gets a rate given its id
	 * @param id of a rate
	 * @return specific rate
	 */
	@Transactional
	public Rate getRate(int id) {
		Rate u = rateRepository.findByRateId(id);
		return u;
	}

	/**
	 * get all rates
	 * @return list of all rates
	 */
	@Transactional
	public List<Rate> getAllRates() {
		return toList(rateRepository.findAll());
	}

	/**
	 * create a new review of a student
	 * @param student that is reviewed
	 * @param tutor that made the review
	 * @param feedback of student made by tutor
	 * @param rating star rating of the student 
	 * @return newly created review
	 */
	@Transactional
	public Review createReview(Student student, Tutor tutor, String feedback, Rating rating) {
		Review u = findReview(student, tutor);
		//There should only be a maximum of 1 review for each student-tutor pair
		if (u == null) { //if review does not yet exist, create it
			u = new Review();
			u.setStudent(student);
			tutor.getReview().add(u);
			student.getReview().add(u);

		}

		u.setFeedback(feedback);
		u.setRating(rating);

		reviewRepository.save(u);

		return u;
	}

	/**
	 * find the review that has the unique student/tutor pair
	 * @param student thats reviewed
	 * @param tutor that is reviewing
	 * @return specific review of student/tutor
	 */
	@Transactional
	public Review findReview(Student student, Tutor tutor) {
		Set<Review> intersect = new HashSet<Review>(getReviews(tutor));
		Set<Review> intersect2 = new HashSet<Review>(getReviews(student));
		intersect.retainAll(intersect2);
		for (Review r:intersect) {
			return r;
		}
//		if (tutor.getReview() == null) {
//			return null;
//		}
//		for (Review r: tutor.getReview()) {
//			if (r.getStudent().equals(student)) {
//				return r;
//			}
//		}
		//if a review does not exist for this pair yet, return null
		return null;
	}

	/**
	 * get all the reviews made by a tutor
	 * @param tutor that has made reviews
	 * @return list of reviews made by the given tutor
	 */
	@Transactional
	public List<Review> getReviews(Tutor tutor) {
		List<Review> u = toList(tutor.getReview());
		return u;
	}

	/**
	 * get all the reviews of a student
	 * @param student that has been reviewed
	 * @return list of all reviews of the given students
	 */
	@Transactional
	public List<Review> getReviews(Student student) {
		List<Review> u = reviewRepository.findReviewByStudent(student);
		return u;
	}

	/**
	 * get review given its id
	 * @param id of review
	 * @return review thats retrieved by id
	 */
	@Transactional
	public Review getReview(int id) {
	    Review u = reviewRepository.findReviewByReviewId(id);
		return u;
	}

	/**
	 * get all the reviews in the system
	 * @return list of all reviews
	 */
	@Transactional
	public List<Review> getAllReviews() {
		return toList(reviewRepository.findAll());
	}

	/**
	 * gets the average rating of all reviews of a student
	 * @param student with reviews
	 * @return rating of the student
	 */
	@Transactional
	public Rating getAverageRating(Student student) {
		int average = 0;
		if(getReviews(student) == null) {
			throw new IllegalArgumentException("No reviews found");
		}
		for (Review aReview:getReviews(student)) {
			Rating r = aReview.getRating();
			switch (r) {
				case FIVE_STAR:
					average+=5;
					break;
				case FOUR_STAR:
					average+=4;
					break;
				case THREE_STAR:
					average+=3;
					break;
				case TWO_STAR:
					average+=2;
					break;
				case ONE_STAR:
					average+=1;
					break;
			default:
				break;
			}
		}
		average =(int) (average / (double)getReviews(student).size());
		if (getReviews(student).size()==0) throw new IllegalArgumentException("No reviews found");
		if (average == 5) return Rating.FIVE_STAR;
		if (average == 4) return Rating.FOUR_STAR;
		if (average == 3) return Rating.THREE_STAR;
		if (average == 2) return Rating.TWO_STAR;
		if (average == 1) return Rating.ONE_STAR;
		else throw new IllegalArgumentException("Unable to find a valid average rating");
	}

	/**
	 * remove a review given its id
	 * @param reviewId id of review
	 */
	@Transactional
	public void removeReview(int reviewId) {
		if (reviewRepository.findReviewByReviewId(reviewId)==null) {
			throw new NullPointerException("No such review exists.");
		}
		reviewRepository.deleteById(reviewId);
	}

	/**
	 * create a new session
	 * @param manager of group session
	 * @param course of the session
	 * @param tutor that teaches the session
	 * @param student list of students that are taking this session
	 * @param room of the session
	 * @param duration of the session in min
	 * @param year of the session (if -1, uses current)
	 * @param monthOfYear of the session (if -1, uses current)
	 * @param dayOfMonth of the session (if -1, uses current)
	 * @param hour of the session (if -1, uses current)
	 * @param minutes of the session (if -1, uses current)
	 * @return newly created session
	 */
	@Transactional
	public Session createSession(Manager manager, Course course, Tutor tutor, List<Student> student, Room room, int duration, int year, int monthOfYear, int dayOfMonth, int hour, int minutes) {
		GroupSession u;
		
		
		if(student.size() > 1){
			u = new GroupSession();
			u.setGroupSize(student.size());
			if (room instanceof RoomBig) {
				((GroupSession) u).setRoom((RoomBig) room);
			}
			else {
				throw new IllegalArgumentException("Room too small for group session.");
			}
		}else {
			throw new IllegalArgumentException("Not Enough Students for a session");
		}
		u.setTutor(tutor);
		u.setManager(manager);
		u.setSessionAccepted(false);
		u.setSessionDuration(duration);
		LocalDateTime dateTime = LocalDateTime.now();
		if (dayOfMonth != -1) {
			dateTime = dateTime.withDayOfMonth(dayOfMonth);
		}
		if (monthOfYear != -1) {
			dateTime = dateTime.withMonth(monthOfYear);
		}
		if (hour != -1) {
			dateTime = dateTime.withHour(hour);
		}
		if (minutes != -1) {
			dateTime = dateTime.withMinute(minutes);
		}
		if (year != -1) {
			dateTime = dateTime.withYear(year);
		}
		u.setDate(dateTime);
		u.setCourse(course);
		Set<Student> students = u.getStudent();
		if (students == null) {
			students = new HashSet<>();
		}
		for(Student s : student) {
			students.add(s);
		}
		u.setStudent(students);

		sessionRepository.save(u);

		return u;
	}

	/**
	 * create a new session
	 * @param course of the session
	 * @param tutor of the session
	 * @param student that wants the session
	 * @param duration of the session in min
	 * @param year of the session (if -1, uses current)
	 * @param monthOfYear of the session (if -1, uses current)
	 * @param dayOfMonth of the session (if -1, uses current)
	 * @param hour of the session (if -1, uses current)
	 * @param minutes of the session (if -1, uses current)
	 * @return newly created session
	 */
	@Transactional
	public Session createSession(Course course, Tutor tutor, Student student, int duration, int year, int monthOfYear, int dayOfMonth, int hour, int minutes) {
		SingleSession u;

		u = new SingleSession();
			

		u.setTutor(tutor);
		u.setSessionDuration(duration);
		LocalDateTime dateTime = LocalDateTime.now();
		if (dayOfMonth != -1) {
			dateTime = dateTime.withDayOfMonth(dayOfMonth);
		}
		if (monthOfYear != -1) {
			dateTime = dateTime.withMonth(monthOfYear);
		}
		if (hour != -1) {
			dateTime = dateTime.withHour(hour);
		}
		if (minutes != -1) {
			dateTime = dateTime.withMinute(minutes);
		}
		if (year != -1) {
			dateTime = dateTime.withYear(year);
		}
		u.setDate(dateTime);
		u.setStudent(student);
		u.setCourse(course);
		sessionRepository.save(u);

		return u;
	}


	/**
	 * get all the sessions of a tutor
	 * @param tutor a tutor with sessions
	 * @return list of sessions
	 */
	@Transactional
	public List<Session> getSessions(Tutor tutor) {
		List<Session> u = sessionRepository.findSessionByTutor(tutor);
		return u;
	}

//	@Transactional
//	public List<Session> getSessions(Student student) {
//		List<Session> u = sessionRepository.findSessionByStudent(student);
//		return u;
//	}

	/**
	 * get all the sessions a course has
	 * @param course that has sessions
	 * @return list of sessions
	 */
	@Transactional
	public List<Session> getSessions(Course course) {
		List<Session> u = sessionRepository.findSessionByCourse(course);
		return u;
	}

//	@Transactional
//	public List<Session> getSessions(Manager manager) {
//		List<Session> u = sessionRepository.findSessionByManager(manager);
//		return u;
//	}

	/**
	 * all the sessions that are being held in a specific room
	 * @param room that has sessions
	 * @return list of sessions
	 */
	@Transactional
	public List<Session> getSessions(Room room) {
		List<Session> u = sessionRepository.findSessionByRoom(room);
		return u;
	}

	/**
	 * gets all the sessions that start at the same time
	 * @param date date and time that the session starts at
	 * @return list of sessions with that start time
	 */
	@Transactional
	public List<Session> getSessions(LocalDateTime date) {
		List<Session> u = sessionRepository.findSessionByDate(date);
		return u;
	}

	/**
	 * get a session by its id
	 * @param ID of the session
	 * @return the specific session
	 */
	@Transactional
	public Session getSession(int ID) {
		Session u = sessionRepository.findSessionBySessionId(ID);
		return u;
	}
	
	/**
	 * get all the accepted sessions of a tutor that start in the future
	 * @param t the tutor that has a session
	 * @return the full list of planned sessions they have
	 */
	@Transactional
	public List<Session> getFutureSessions(Tutor t) {
		List<Session> u = sessionRepository.findSessionByTutorAndSessionAccepted(t, true);
		List<Session> ss = new ArrayList<>();
		LocalDateTime time = LocalDateTime.now();
		for(Session s:u) {
			if(s.getDate().isAfter(time)) {
				ss.add(s);
			}
		}
		return ss;
	}
	
	/**
	 * get all the past sessions that the tutor has accepted and started in the past
	 * @param t the tutor that has had a session
	 * @return list of all their past sessions
	 */
	@Transactional
	public List<Session> getPastSessions(Tutor t) {
		List<Session> u = sessionRepository.findSessionByTutorAndSessionAccepted(t, true);
		List<Session> ss = new ArrayList<>();
		LocalDateTime time = LocalDateTime.now();
		for(Session s:u) {
			if(s.getDate().isBefore(time)) {
				ss.add(s);
			}
		}
		return ss;
	}

	/**
	 * get all the sessions in the system
	 * @return list of all sessions
	 */
	@Transactional
	public List<Session> getAllSessions() {
		return toList(sessionRepository.findAll());
	}

	/**
	 * sets a session as accepted
	 * @param ID the id of the session
	 */
	@Transactional
	public void acceptSession(int ID) {
	  Session u = sessionRepository.findSessionBySessionId(ID);
	  if (u instanceof SingleSession) {
		  if(!setRoomByTime((SingleSession) u)) {
			  throw new IllegalArgumentException("There are no empty rooms at this date and time");
		  }
	  }
	  u.setSessionAccepted(true);
	}

	/**
	 * sets the session as not accepted
	 * @param ID of the session
	 */
	@Transactional
    public void refuseSession(int ID) {
      Session u = sessionRepository.findSessionBySessionId(ID);
      u.setSessionAccepted(false);
    }

	/**
	 * removes a specific session
	 * @param sessionID the id number of the session
	 */
	@Transactional
	public void removeSession(int sessionID) {
	  if (sessionRepository.findSessionBySessionId(sessionID) == null) {
	    throw new NullPointerException("No such session exists");
	  }

	  sessionRepository.deleteById(sessionID);
	}

	/**
	 * gets all the sessions of a tutor that are pending acceptance
	 * @param tutor that has requests
	 * @return list of all their requested sessions
	 */
	@Transactional
	public List<Session> getPendingSessions(Tutor tutor){
		List<Session> s = sessionRepository.findSessionByTutorAndSessionAccepted(tutor, false);
		return s;
	}
	
	/**
	 * gets all the single sessions of a tutor that are pending acceptance
	 * @param tutor that has requests
	 * @return list of all their requested single sessions
	 */
	@Transactional
	public List<SingleSession> getPendingSingleSessions(Tutor tutor){
		List<SingleSession> s = new ArrayList<>();
		for(Session ses:getPendingSessions(tutor)) {
			if(ses instanceof SingleSession) {
				s.add((SingleSession)ses);
			}
		}
		return s;
	}
	
	/**
	 * gets all the group sessions of a tutor that are pending acceptance
	 * @param tutor that has requests
	 * @return list of all their requested group sessions
	 */
	@Transactional
	public List<GroupSession> getPendingGroupSessions(Tutor tutor){
		List<GroupSession> s = new ArrayList<>();
		for(Session ses:getPendingSessions(tutor)) {
			if(ses instanceof GroupSession) {
				s.add((GroupSession)ses);
			}
		}
		return s;
	}
	

	/**
	 * gets a role given its id number
	 * @param roleId id of the role
	 * @return the specified UserRole
	 */
	@Transactional
	public UserRole getRole(int roleId) {
		if (roleRepository.findById(roleId).isPresent()) {
			UserRole u = roleRepository.findById(roleId).get();
			return u;
		}
		return null;
	}

	/**
	 * turns an iterable type into a list
	 * @param <T> the type of list to create
	 * @param iterable any iterable collection
	 * @return a list that contains all the objects of the collection
	 */
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	/**
	 * gets a room by its number
	 * @param roomNumber of the room
	 * @return the room with the given number
	 */
	@Transactional
	public Room getRoom(Integer roomNumber) {
		return roomRepository.findByRoomNumber(roomNumber);
	}
	
	/**
	 * sets the room of the single session based on its start time and duration.
	 * choosing a random room from the available rooms.
	 * @param s single session that is looking for a room
	 * @return true if it was able to set a room, false if all the rooms are booked
	 */
	@Transactional
	public boolean setRoomByTime(SingleSession s) {
		List<RoomSmall> smallRooms = getAllSmallRooms();
		List<RoomSmall> openRooms = new ArrayList<>();
		
		for(RoomSmall r:smallRooms) {
			if (roomOpen(s,r)) {
				openRooms.add(r);
			}
		}
		int numOpen = openRooms.size();
		if (numOpen == 0) {
			return false;
		}
		
		int index = ran.nextInt(numOpen);
		RoomSmall room = openRooms.get(index);
		room.getSingleSession().add(s);
		s.setRoom(room);
		
		return true;
	}
	
	/**
	 * gets all the small rooms
	 * @return list of all small rooms
	 */
	@Transactional
	public List<RoomSmall> getAllSmallRooms() {
		List<RoomSmall> allSmallRooms = new ArrayList<>();
		for(Room room:roomRepository.findAll()) {
			if (room instanceof RoomSmall) {
				allSmallRooms.add((RoomSmall) room);
			}
		}
		return allSmallRooms;
	}
	
	/**
	 * determines if a specific room is available for the single session's start time and duration
	 * @param s single session that contains a time
	 * @param r room that is being checked if available
	 * @return true if the room is available, else false
	 */
	@Transactional
	public boolean roomOpen(SingleSession s, RoomSmall r) {
		LocalDateTime sTime = s.getDate();
		LocalDateTime eTime = sTime.plusMinutes(s.getSessionDuration());
		
		for(Session session:r.getSingleSession()) {
			LocalDateTime sessionTime = session.getDate();
					
			if(sessionTime.toLocalDate().equals(sTime.toLocalDate())) { //if they share a date, check in more detail
				if(sessionTime.isAfter(sTime) && sessionTime.isBefore(eTime)) {
					return false;
				}
				LocalDateTime endTime = sessionTime.plusMinutes(session.getSessionDuration());
				if(endTime.isAfter(sTime) && endTime.isBefore(eTime)) {
					return false;
				}
				if(sessionTime.isBefore(sTime) && endTime.isAfter(eTime)) {
					return false;
				}
				if(sessionTime.equals(sTime)) {
					return false;
				}
				if(endTime.equals(eTime)) {
					return false;
				}
			}
		}
		return true;
		
	}

}
