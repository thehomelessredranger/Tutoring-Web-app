package ca.mcgill.ecse321.backend.controller;

import ca.mcgill.ecse321.backend.model.*;

import java.util.*;

import ca.mcgill.ecse321.backend.dto.*;

public class DtoConverters {

	/**
	 * Check if an object is null and throw IllegalArgumentException in that case.
	 *
	 * @param o the object to check
	 */
	@SuppressWarnings("null")
	static void checkArg(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Trying to convert using a null " + o.getClass().getSimpleName());
		}
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param a Availability to convert
	 * @return Data Transfer Object
	 */
	public static AvailabilityDto convertToDto(Availability a) {
		checkArg(a);
		Tutor t = a.getTutor();
		// TutorDto t = convertToDto(a.getTutor());
		return new AvailabilityDto(t.getId(), a.getStartTime(), a.getEndTime(), a.getDayOfWeek(), a.getId());
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param c Course to convert
	 * @return Course Data Transfer Object
	 */
	public static CourseDto convertToDto(Course c) {
		checkArg(c);

		List<Integer> tutor = new ArrayList<>();
		List<RateDto> rates = new ArrayList<>();
		for (Tutor t : c.getTutor()) {
			tutor.add(t.getId());
			for (Rate r : t.getRate()) {
				if (r.getCourse().getCourseId().equals(c.getCourseId())) {
					rates.add(convertToDto(r));
				}
			}
		}
		return new CourseDto(tutor, c.getSubject(), c.getCourseNumber(), c.getCourseName(), c.getCourseId(), rates);
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param s Session to convert
	 * @return Session Data Transfer Object
	 */
	public static SessionDto convertToDto(Session s) {
		checkArg(s);
		if (s instanceof GroupSession) {
			return convertToDto((GroupSession) s);
		} else if (s instanceof SingleSession) {
			return convertToDto((SingleSession) s);
		} else {
			throw new IllegalArgumentException("This Session is not an instance of any subclass");
		}
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param gs GroupSession to convert
	 * @return GroupSession Data Transfer Object
	 */
	public static GroupSessionDto convertToDto(GroupSession gs) {
		checkArg(gs);
		int manager = gs.getManager().getId();
		String course = gs.getCourse().getCourseId();
		int tutor = gs.getTutor().getId();
		List<Integer> students = new ArrayList<>();

		for (Student st : gs.getStudent()) {
			students.add(st.getId());
		}
		return new GroupSessionDto(manager, course, tutor, students, gs.getRoom().getRoomNumber(),
				gs.getSessionDuration(), gs.getId(), gs.getGroupSize());
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param ss SingleSession to convert
	 * @return SingleSession Data Transfer Object
	 */
	public static SingleSessionDto convertToDto(SingleSession ss) {
		checkArg(ss);
		String course = ss.getCourse().getCourseId();
		int tutor = ss.getTutor().getId();

		return new SingleSessionDto(course, tutor, ss.getStudent().getId(), ss.getRoom().getRoomNumber(),
				ss.getSessionDuration(), ss.getId());
	}

	/**
	 * Convert Data object to Dto
	 * 
	 * @param rate Rate to convert
	 * @return Rate Data Transfer Object
	 */
	public static RateDto convertToDto(Rate rate) {
		checkArg(rate);
		int t = rate.getTutor().getId();
		String c = rate.getCourse().getCourseId();

		return new RateDto(rate.getRate(), t, c, rate.getId());
	}

	/**
	 * Convert Data object to Dto
	 * @param review to convert
	 * @return Review Data Transfer Object
	 */
	public static ReviewDto convertToDto(Review review) {
		checkArg(review);
		Rating rating = review.getRating();
		int integerRating = ratingToInt(rating);
		if (review.getFeedback() != null) {
			return new ReviewDto(review.getStudent().getId(), integerRating, review.getFeedback(), review.getId());
		}
		return new ReviewDto(review.getStudent().getId(), integerRating, review.getId());
	}

	/**
	 * Convert Data object to Dto
	 * @param role UserRole to convert
	 * @return UserRole Data Transfer Object
	 */
	public static UserRoleDto convertToDto(UserRole role) {
		checkArg(role);

		if (role instanceof Student) {
			return convertToDto((Student) role);
		} else if (role instanceof Tutor) {
			return convertToDto((Tutor) role);
		} else if (role instanceof Manager) {
			return convertToDto((Manager) role);
		} else {
			throw new IllegalArgumentException("This UserRole is not an instance of any subclass");
		}
	}

	/**
	 * Convert Data object to Dto
	 * @param u User to convert
	 * @return User Data Transfer Object
	 */
	public static UserDto convertToDto(User u) {
		checkArg(u);
		UserRoleDto r = convertToDto(u.getUserRole());
		return new UserDto(u.getName(), r, u.getId());
//		u.getPassword()
	}

	/**
	 * Convert Data object to Dto
	 * @param s Student to convert
	 * @return Student Data Transfer Object
	 */
	public static StudentDto convertToDto(Student s) {
		int rating = ratingToInt(s.getAverageRating());
		StudentDto student = new StudentDto(s.getId(), s.getUser().getId(), rating);
		List<ReviewDto> reviews = new ArrayList<>();
		List<SessionDto> sessions = new ArrayList<>();
		for (Review r : s.getReview()) {
			reviews.add(convertToDto(r));
		}
		for (Session sess : s.getSession()) {
			sessions.add(convertToDto(sess));
		}
		student.setReviews(reviews);
		student.setSessions(sessions);
		return student;
	}

	/**
	 * Convert Data object to Dto
	 * @param t Tutor to convert
	 * @return Tutor Data Transfer Object
	 */
	public static TutorDto convertToDto(Tutor t) {
		TutorDto tutor = new TutorDto(t.getId(), t.getUser().getId(), t.isIsVerified(), t.getManager().getId());
		List<ReviewDto> reviews = new ArrayList<>();
		List<AvailabilityDto> availabilities = new ArrayList<>();
		List<CourseDto> courses = new ArrayList<>();
		List<SessionDto> sessions = new ArrayList<>();
		List<RateDto> rates = new ArrayList<>();
		for (Review r : t.getReview()) {
			reviews.add(convertToDto(r));
		}
		for (Availability a : t.getAvailability()) {
			availabilities.add(convertToDto(a));
		}
		for (Course c : t.getCourse()) {
			courses.add(convertToDto(c));
		}
		for (Session s : t.getSession()) {
			sessions.add(convertToDto(s));
		}
		for (Rate r : t.getRate()) {
			rates.add(convertToDto(r));
		}
		tutor.setReviews(reviews);
		tutor.setAvailabilities(availabilities);
		tutor.setCourses(courses);
		tutor.setSessions(sessions);
		tutor.setRates(rates);
		return tutor;
	}

	/**
	 * Convert Data object to Dto
	 * @param m Manager to convert
	 * @return Manager Data Transfer Object
	 */
	public static ManagerDto convertToDto(Manager m) {
		ManagerDto manager = new ManagerDto(m.getId(), m.getUser().getId());
		List<SessionDto> sessions = new ArrayList<>();
		List<TutorDto> tutors = new ArrayList<>();
		for (Session s : m.getSession()) {
			sessions.add(convertToDto(s));
		}
		for (Tutor t : m.getTutor()) {
			tutors.add(convertToDto(t));
		}
		manager.setSessions(sessions);
		manager.setTutors(tutors);
		return manager;
	}

	/**
	 * Convert Rating Enum object to int type
	 * @param rating from one star to five stars
	 * @return an int from 1 to 5 representing the rating
	 */
	public static int ratingToInt(Rating rating) {
		int integerRating;
		if (rating == Rating.FIVE_STAR) {
			integerRating = 5;
		} else if (rating == Rating.FOUR_STAR) {
			integerRating = 4;
		} else if (rating == Rating.THREE_STAR) {
			integerRating = 3;
		} else if (rating == Rating.TWO_STAR) {
			integerRating = 2;
		} else if (rating == Rating.ONE_STAR) {
			integerRating = 1;
		} else if (rating == Rating.NO_RATING) {
			integerRating = 0;
		} else {
			throw new IllegalArgumentException("Not a valid Rating");
		}
		return integerRating;
	}

	/**
	 * Convert Data object to Dto
	 * @param q Qualification to convert
	 * @return Qualification Data Transfer Object
	 */
	public static QualificationDto convertToDto(Qualification q) {
		checkArg(q);
		return new QualificationDto(q.getSchool(), q.getDegree(), q.getStartYear(), q.getEndYear(), q.isOnGoing());
	}

}
