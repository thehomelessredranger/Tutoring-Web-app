	package ca.mcgill.ecse321.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.backend.dto.*;
import ca.mcgill.ecse321.backend.model.*;
import ca.mcgill.ecse321.backend.service.MasterService;

@CrossOrigin(origins = "*")
@RestController
public class TutorViewpointRestController {

	@Autowired
	private MasterService service;


	/**
	 * Create a new user with given values.
	 * @param idNumber optional idnumber
	 * @param name username
	 * @param password initial user password
	 * @param Type of user: Tutor, Manager, Student
	 * @return UserDto of user thats just been made
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping(value = { "/signup", "/signup/"} )
	public UserDto createUser(@RequestParam(required = false) Integer idNumber, @RequestParam String name,
			@RequestParam String password, @RequestParam String Type) throws IllegalArgumentException {
		User u;
		if (idNumber!=null) {
			u = service.createUser(idNumber, name, password, Type);
		} else {
			u = service.createUser(name, password, Type);
		}

		return DtoConverters.convertToDto(u);

	}

	/**
	 * Get user if given right username and password
	 * @param username of user
	 * @param password of user
	 * @return UserDto of logged in user
	 */
	@GetMapping(value = {"/login", "/login/"} )
	public UserDto loginToUserName(@RequestParam String username, @RequestParam String password) {

		User u;
		u = service.logIn(username, password);

		return DtoConverters.convertToDto(u);
	}


	/**
	 * get a list of all users in system
	 * @return list of UserDtos of all users
	 */
	@GetMapping(value = {"/users", "/users/"})
	public List<UserDto> getAllUsers() {

		List<UserDto> userDtos = new ArrayList<UserDto>();
		for (User u : service.getAllUsers()) {
			userDtos.add(DtoConverters.convertToDto(u));
		}

		return userDtos;

	}

	/**
	 * get a specific user
	 * @param username of user
	 * @param id number of user
	 * @return user's Dto
	 */
	@GetMapping(value = {"/user/{username}/{id}", "/user/{username}/{id}/"})
	public UserDto getUser(@PathVariable("username") String username, @PathVariable("id") int id) {

		User user = service.getUser(id);
		if(!user.getName().equals(username)) {
			user = null;
		}

		return DtoConverters.convertToDto(user);
	}

	/**
	 * change user password
	 * @param username of user
	 * @param id number of user
	 * @param password new password to change to
	 * @return UserDto of user
	 */
	@PostMapping(value = {"/user/{username}/{id}", "/user/{username}/{id}/"})
	public UserDto changePass(@PathVariable("username") String username, @PathVariable("id") int id, @RequestParam String password) {

		User user = service.getUser(id);
		if(!user.getName().equals(username)) {
			user = null;
		}
		service.changePassword(id, password);
		return DtoConverters.convertToDto(user);
	}



	/*@PostMapping (value = { "/qualifications/{id}", "/qualifications/{id}/"} )
	public Set<QualificationDto> setQualificationByTutor(@PathVariable("id") TutorDto tutor,
			@RequestParam(name = "qualifications") List<QualificationDto> qualificationDtos) {

		Set<Qualification> q = tutor.getQualification();
		tutor.setQualification(q);

		Set<QualificationDto> qDto = new HashSet<QualificationDto>();


		for(Qualification q_iterable: q) {
			qDto.add(DtoConverters.convertToDto(q_iterable));
		}
		return qDto;

	}

	@GetMapping(value = { "/qualifications", "/qualifications/"} )
	public Set<QualificationDto> findQualificationByTutor(@RequestParam TutorDto tutor){

		Set<Qualification> q = tutor.getQualification();
		Set<QualificationDto> qdto = new HashSet<>();

		for(Qualification q_iterable: q) {
			qdto.add(DtoConverters.convertToDto(q_iterable));
		}
		return qdto;
	}*/

	/**
	 * create a new session with given stats.
	 * @param managerDto optional manager of group session
	 * @param courseDto course of session
	 * @param tutorDto desired tutor of session
	 * @param studentDtos list of students for group session (optional, required if no studentDto (Required for GroupSession))
	 * @param studentDto student of single session (optional, required if no studentDtos (Required for SingleSession))
	 * @param room of group session (required for group session, otherwise optional)
	 * @param duration of session in minutes
	 * @param year of session (optional, default to current year)
	 * @param month number of session (optional, default to current month)
	 * @param day of month of session (optional, default to current day)
	 * @param hour of session (optional, default to current hour)
	 * @param minute of session (optional, default to current minute)
	 * @return the newly created session
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping("/sessions") //if any of the date/times is not given, it defaults to the current one.
	public SessionDto createSession(@RequestParam(required = false) ManagerDto managerDto, @RequestParam CourseDto courseDto, @RequestParam TutorDto tutorDto,
			@RequestParam(name = "students", required = false) List<StudentDto> studentDtos, @RequestParam(name = "student", required = false) StudentDto studentDto,
			@RequestParam(required = false) RoomDto room, @RequestParam int duration, @RequestParam(defaultValue  = "-1") int year, @RequestParam(defaultValue  = "-1") int month,
			@RequestParam(defaultValue  = "-1") int day, @RequestParam(defaultValue  = "-1") int hour, @RequestParam(defaultValue  = "-1") int minute) throws IllegalArgumentException {

		Course c = service.getCourse(courseDto.getCourseId());
		Tutor t = (Tutor)service.getUser(tutorDto.getIdNumber()).getUserRole();
		Session s = null;

		if (studentDtos != null) {
			List<Student> students = new ArrayList<Student>();

			for (StudentDto sdto : studentDtos) {
				students.add((Student)service.getUser(sdto.getIdNumber()).getUserRole());
			}
			s = service.createSession((Manager)service.getUser(managerDto.getIdNumber()).getUserRole(),
					c, t, students, service.getRoom(room.getRoomNum()), duration, year, month, day, hour, minute);
		} else if(studentDto != null) {
			Student student = (Student)service.getUser(studentDto.getIdNumber()).getUserRole();
			s = service.createSession(c, t, student, duration, year, month, day, hour, minute);
		}


		return DtoConverters.convertToDto(s);
	}

	/**
	 * get a list of all sessions
	 * @return list of SessionDto of all sessions
	 */
	@GetMapping(value = {"/sessions", "/sessions/"})
	public List<SessionDto> getAllSessions() {

		List<SessionDto> sessionDtos = new ArrayList<SessionDto>();
		for (Session s : service.getAllSessions()) {
			sessionDtos.add(DtoConverters.convertToDto(s));
		}

		return sessionDtos;

	}

	/*
	@GetMapping(value = {"/Sessions/{RoleID}", "/Sessions/{RoleID}/"})
	public List<SessionDto> getPendingSessions(@PathVariable("RoleID") int roleId,  @RequestParam(defaultValue  = "All") String type) {
		if(!(service.getRole(roleId) instanceof Tutor)) return null;
		List<SessionDto> SessionDtos = new ArrayList<SessionDto>();
		if(type.equals("All")) {
			for (Session s : service.getPendingSessions((Tutor)service.getRole(roleId))) {
				SessionDtos.add(DtoConverters.convertToDto(s));
			}
		} else if(type.equals("Single")) {
			for (Session s : service.getPendingSingleSessions((Tutor)service.getRole(roleId))) {
				SessionDtos.add(DtoConverters.convertToDto(s));
			}
		} else if(type.equals("Group")) {
			for (Session s : service.getPendingGroupSessions((Tutor)service.getRole(roleId))) {
				SessionDtos.add(DtoConverters.convertToDto(s));
			}
		}

		return SessionDtos;

	}
	*/

	/**
	 * get all pending single sessions of a user
	 * @param roleId id of user
	 * @return List of single session Dtos
	 */
	@GetMapping(value = {"/SingleSessions/{RoleID}", "/SingleSessions/{RoleID}/"})
	public List<SingleSessionDto> getPendingSingleSessions(@PathVariable("RoleID") int roleId) {
		List<SingleSessionDto> SessionDtos = new ArrayList<>();
		if(!(service.getRole(roleId) instanceof Tutor)) return null;

		for (SingleSession s : service.getPendingSingleSessions((Tutor)service.getRole(roleId))) {
			SessionDtos.add(DtoConverters.convertToDto(s));
		}

		return SessionDtos;
	}

	/**
	 * get all pending group sessions of a user
	 * @param roleId id of user
	 * @return List of group session Dtos
	 */
	@GetMapping(value = {"/GroupSessions/{RoleID}", "/GroupSessions/{RoleID}/"})
	public List<GroupSessionDto> getPendingGroupSessions(@PathVariable("RoleID") int roleId) {
		List<GroupSessionDto> SessionDtos = new ArrayList<>();
		if(!(service.getRole(roleId) instanceof Tutor)) return null;

		for (GroupSession s : service.getPendingGroupSessions((Tutor)service.getRole(roleId))) {
			SessionDtos.add(DtoConverters.convertToDto(s));
		}

		return SessionDtos;
	}

	/**
	 * mark a session as accepted
	 * @param sessionID of session to accept
	 */
	@PutMapping(value = {"/AcceptSession/{SessionID}", "/AcceptSession/{SessionID}/"})
	public void acceptSession(@PathVariable("SessionID") int sessionID) {

		service.acceptSession(sessionID);


	}

	/**
	 * create a review of a student
	 * @param studentDto that is being reviewd
	 * @param tutorDto making the review
	 * @param feedback of student
	 * @param rating of student
	 * @return reviewDto
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping("/createReview")
	public ReviewDto createReview(@RequestParam StudentDto studentDto,  @RequestParam TutorDto tutorDto,
		 @RequestParam String feedback, @RequestParam Rating rating) throws IllegalArgumentException {


		Review s = service.createReview((Student)service.getUser(studentDto.getIdNumber()).getUserRole(),
				 (Tutor)service.getUser(tutorDto.getIdNumber()).getUserRole(), feedback, rating);

		return DtoConverters.convertToDto(s);
	}


	/**
	 * get all the reviews
	 * @return list of ReviewDtos of all reviews
	 */
	@GetMapping(value = {"/allReviews", "/allReviews/"})
	public List<ReviewDto> getAllReviews() {

		List<ReviewDto> ReviewDtos = new ArrayList<ReviewDto>();
		for (Review s : service.getAllReviews()) {
			ReviewDtos.add(DtoConverters.convertToDto(s));
		}

		return ReviewDtos;

	}

	/**
	 * remove a review of a student
	 * @param reviewId id of review to remove
	 * @return true if removed
	 */
	@PostMapping(value = { "/removeReview", "/removeReview/" })
	public boolean removeReview(@RequestParam int reviewId) {
		service.removeReview(reviewId);
		return true;
		}

	/**
	 * get all reviews of a student
	 * @param studentDto reviewd student
	 * @return list of ReviewDtos of given student
	 */
	@GetMapping(value = {"/studentsReviews", "/studentsReviews/"})
	public List<ReviewDto> getStudentReviews(@RequestParam StudentDto studentDto) {
		Student s =(Student) service.getUser(studentDto.getUserId()).getUserRole();
		List<ReviewDto> ReviewDtos = new ArrayList<ReviewDto>();
		for (Review r : service.getReviews(s)) {
			ReviewDtos.add(DtoConverters.convertToDto(r));
		}
		return ReviewDtos;

	}
	/**
	 * get all reviews of a tutor
	 * @param tutorDto reviewer
	 * @return list of ReviewDtos made by given tutor
	 */
	@GetMapping(value = {"/TutorsReviews", "/TutorsReviews/"})
	public List<ReviewDto> getTutorReviews(@RequestParam TutorDto tutorDto) {
		Tutor t =(Tutor) service.getUser(tutorDto.getUserId()).getUserRole();
		List<ReviewDto> ReviewDtos = new ArrayList<ReviewDto>();
		for (Review r : service.getReviews(t)) {
			ReviewDtos.add(DtoConverters.convertToDto(r));
		}
		return ReviewDtos;
	}

	/**
	 * get average rating of a student
	 * @param studentDto student with rating
	 * @return rating from 1-5 of student
	 */
	@GetMapping(value = {"/studentRating", "/studentRating/"})
	public int getStudentRating(@RequestParam StudentDto studentDto) {
		Student s =(Student) service.getUser(studentDto.getUserId()).getUserRole();

		return DtoConverters.ratingToInt(service.getAverageRating(s));

	}

	/**
	 * remove a rate
	 * @param rateId id of rate to remove
	 * @return true if rate removed
	 */
	@PostMapping(value = { "/removeRates", "/removeRates/" })
    public boolean removeRate(@RequestParam int rateId) {
        service.removeRate(rateId);
        return true;
        }

	/**
	 * create a new rate for a tutor/course pair
	 * @param rate value of tutor's rate
	 * @param tutorId tutor's id value
	 * @param courseId course id value
	 * @return RateDto of new rate
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping("/createRates")
	public RateDto createRate(@RequestParam int rate,  @RequestParam int tutorId,
			@RequestParam String courseId) throws IllegalArgumentException {


		Rate s = service.createRate(rate, (Tutor)service.getUser(tutorId).getUserRole(),
				service.getCourse(courseId));

		return DtoConverters.convertToDto(s);
	}

	/**
	 * change rate
	 * @param rateDto to change
	 * @param rate of tutor to change to
	 * @return RateDto of changed rate
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PutMapping("/changeRate")
	public RateDto changeRate(@RequestParam RateDto rateDto,  @RequestParam int rate) throws IllegalArgumentException {

		Rate s = service.getRate(rateDto.getId());
		service.changeRate(rateDto.getId(), rate);

		return DtoConverters.convertToDto(s);
	}

	/**
	 * gets all rates
	 * @return list of RateDtos of all rates
	 */
	@GetMapping(value = {"/getRates", "/getRates/"})
	public List<RateDto> getAllRates() {

		List<RateDto> RateDtos = new ArrayList<RateDto>();
		for (Rate s : service.getAllRates()) {
			RateDtos.add(DtoConverters.convertToDto(s));
		}

		return RateDtos;

	}

	/**
	 * gets the rate of a tutor for a specific course
	 * @param tutorId id of tutor
	 * @param courseId id of course
	 * @return rate of tutor for a course
	 */
	@GetMapping(value = {"Tutors/{tutorId}/Rates/{courseId}", "Tutors/{tutorId}/Rates/{courseId}/"})
	public int getRate(@PathVariable("tutorId") int tutorId, @PathVariable("courseId") String courseId) {
		Course c = service.getCourse(courseId);
		Tutor t = (Tutor) service.getRole(tutorId);
		Rate r = service.getRate(c,t);
		return r.getRate();
	}

	/**
	 * gets list of all future sessions for a specific tutor
	 * @param tutorId of tutor to check
	 * @return list of future sessions as SessionDtos
	 */
	@GetMapping(value = {"Tutors/{tutorId}/Sessions/Future", "Tutors/{tutorId}/Sessions/Future/"})
	public List<SessionDto> getFutureSessions(@PathVariable("tutorId") int tutorId) {
		List<SessionDto> sessions = new ArrayList<>();
		Tutor t = (Tutor) service.getRole(tutorId);
		for (Session s: service.getFutureSessions(t)) {
			sessions.add(DtoConverters.convertToDto(s));
		}
		return sessions;
	}

	/**
	 * gets list of all past sessions for a specific tutor
	 * @param tutorId of tutor to check
	 * @return list of past sessions as SessionDtos
	 */
	@GetMapping(value = {"Tutors/{tutorId}/Sessions/Past", "Tutors/{tutorId}/Sessions/Past/"})
	public List<SessionDto> getPastSessions(@PathVariable("tutorId") int tutorId) {
		List<SessionDto> sessions = new ArrayList<>();
		Tutor t = (Tutor) service.getRole(tutorId);
		for (Session s: service.getPastSessions(t)) {
			sessions.add(DtoConverters.convertToDto(s));
		}
		return sessions;
	}

	/**
	 * gets average rate of all tutors for a course
	 * @param courseId id of course that's being checked
	 * @return average rate of all tutors
	 */
	@GetMapping(value = {"Courses/{courseId}/Rate", "Courses/{courseId}/Rate/"})
	public double getRate(@PathVariable("courseId") String courseId) {
		Course c = service.getCourse(courseId);
		double rate = service.getAverageRate(c);
		return rate;
	}


	/**
	 * get all rates of a course
	 * @param CourseDto of the rates
	 * @return list of all rates of a course as RateDtos
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@GetMapping(value = {"/Rates", "/Rates/"})
	public List<RateDto> getAllRates(@RequestParam CourseDto CourseDto) throws IllegalArgumentException {


		List<RateDto> RateDtos = new ArrayList<RateDto>();

		for (Rate s : service.getRates(service.getCourse(CourseDto.getCourseId()))) {
			RateDtos.add(DtoConverters.convertToDto(s));
		}

		return RateDtos;

	}

	/**
	 * create a new course
	 * @param subject type of course (eg. ECSE)
	 * @param number course number (eg. 321)
	 * @param name description of course (eg. intro to software engineering)
	 * @return new course as Dto
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping("/CreateCourse")
	public CourseDto createCourse(@RequestParam String subject, @RequestParam int number, @RequestParam String name) throws IllegalArgumentException {

		Course s = service.createCourse(subject, number, name);

		return DtoConverters.convertToDto(s);
	}

	/**
	 * delete a course
	 * @param courseId of course to remove
	 */
	@DeleteMapping(value = {"/Courses/{courseId}", "/Courses/{courseId}/"})
	public void deleteCourse(@PathVariable("courseId") String courseId) {
		service.removeCourse(courseId);
	}

	/**
	 * remove a tutor from a course
	 * @param tutorId id of tutor to remove
	 * @param courseId course id of course to remove them from
	 */
	@PostMapping(value = {"/Tutors/{tutorId}/{courseId}", "/Tutors/{tutorId}/{courseId}/"})
	public void removeTutorFromCourse(@PathVariable("tutorId") int tutorId, @PathVariable("courseId") String courseId) {
		Course course = service.getCourse(courseId);
		Tutor tutor =(Tutor) service.getRole(tutorId);
		service.removeTutorFromCourse(tutor, course);
	}

	/**
	 * add a tutor to a course
	 * @param tutorId id of tutor to add to course
	 * @param courseId course id of course to add tutor to
	 */
	@PostMapping(value = {"/Courses/{courseId}/{tutorId}", "/Courses/{courseId}/{tutorId}/"})
	public void addTutorToCourse(@PathVariable("tutorId") int tutorId, @PathVariable("courseId") String courseId) {
		Course course = service.getCourse(courseId);
		Tutor tutor =(Tutor) service.getRole(tutorId);
		service.addTutorToCourse(tutor, course);
	}

	/**
	 * get list of all courses
	 * @return list of all courses as Dtos
	 */
	@GetMapping(value = {"/Courses", "/Courses/"})
	public List<CourseDto> getAllCourses() {

		List<CourseDto> CourseDtos = new ArrayList<CourseDto>();
		for (Course s : service.getAllCourses()) {
			CourseDtos.add(DtoConverters.convertToDto(s));
		}

		return CourseDtos;

	}


	/**
	 * list of all courses a tutor teaches
	 * @param idNumber of tutor
	 * @return list of courses they teach as DTOs
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@GetMapping(value = {"/Courses/{idNumber}", "/Courses/{idNumber}"})
	public List<CourseDto> getAllCourses(@PathVariable("idNumber") Integer idNumber) throws IllegalArgumentException {

		Tutor t;
		if(!(service.getUser(idNumber).getUserRole() instanceof Tutor)) {
			throw new IllegalArgumentException("Not a Tutor");
		}
		else {
			t = (Tutor) service.getUser(idNumber).getUserRole();
		}

		List<CourseDto> CourseDtos = new ArrayList<CourseDto>();
		for (Course s : service.getCourses(t)) {
			CourseDtos.add(DtoConverters.convertToDto(s));
		}

		return CourseDtos;

	}


	/**
	 * create a new availability for a tutor
	 * @param tutorDto of new availability
	 * @param startTime of availability
	 * @param endTime of availability
	 * @param day of availability
	 * @return new availability as DTO
	 * @throws IllegalArgumentException if missing arguments or bad argument
	 */
	@PostMapping("/newAvailability")
	public AvailabilityDto createAvailability(@RequestParam TutorDto tutorDto,
			@RequestParam int startTime, @RequestParam int endTime, @RequestParam Day day) throws IllegalArgumentException {


		Availability s = service.createAvailability((Tutor)service.getUser(tutorDto.getIdNumber()).getUserRole(),
				startTime, endTime, day);

		return DtoConverters.convertToDto(s);
	}

	/**
	 * gets a list of all availabilities
	 * @return list of all availabilities as availability DTOs
	 */
	@GetMapping(value = {"/Availabilities", "/Availabilities/"})
	public List<AvailabilityDto> getAllAvailabilities() {

		List<AvailabilityDto> AvailabilityDtos = new ArrayList<AvailabilityDto>();
		for (Availability s : service.getAllAvailabilities()) {
			AvailabilityDtos.add(DtoConverters.convertToDto(s));
		}

		return AvailabilityDtos;

	}

	/**
	 * get all availabilities of a tutor
	 * @param roleId id of tutor
	 * @return list of availabilities of a tutor as DTOs
	 */
	@GetMapping(value = {"/Availabilities/{RoleID}", "/Availabilities/{RoleID}"})
	public List<AvailabilityDto> getAvailabilitiesByTutor(@PathVariable("RoleID") int roleId) {

		List<AvailabilityDto> AvailabilityDtos = new ArrayList<AvailabilityDto>();
		if(!(service.getRole(roleId) instanceof Tutor)) return null;

		for (Availability s : service.getAvailabilities((Tutor)service.getRole(roleId))) {
			AvailabilityDtos.add(DtoConverters.convertToDto(s));
		}

		return AvailabilityDtos;

	}

	/**
	 * delete an availability of a tutor
	 * @param availabilityId id of availability to delete
	 */
	@DeleteMapping(value = {"/Availabilities/{AvailabilityID}", "/Availabilities/{AvailabilityID}"})
	public void deleteAvailability(@PathVariable("AvailabilityID") int availabilityId) {

		service.removeAvailability(availabilityId);

	}

	@DeleteMapping(value = {"/DELETEALLUSERS/{pass}", "/DELETEALLUSERS/{pass}/"})
	public void deleteAllUsers(@PathVariable("pass") String pass) {

		if(!pass.equals("adminpassword")) {
			return;
		}
		for(User u : service.getAllUsers()) {
			service.removeUser(u.getId());
		}

	}


}
