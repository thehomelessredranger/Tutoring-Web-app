package ca.mcgill.ecse321.backend.dto;

import java.util.List;
import java.util.Set;

public class TutorDto extends UserRoleDto{

	private List<ReviewDto> reviews;
	private boolean isVerified;
	private List<AvailabilityDto> availabilities;
	private List<CourseDto> courses;
	private List<SessionDto> sessions;
	private List<RateDto> rates;
	private Set<QualificationDto> qualification;

	int managerId;

	public TutorDto(int id, int userId, boolean isVerified, int managerId) {
		super(id, userId);
		this.isVerified = isVerified;
		this.managerId = managerId;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}
	public List<ReviewDto> getReviews() {
		return reviews;
	}

	public void setIsVerified(boolean isVerified) {
	  this.isVerified = isVerified;
	}

	public boolean getIsVerified() {
	  return isVerified;
	}

	public void setAvailabilities(List<AvailabilityDto> availabilities) {
		this.availabilities = availabilities;
	}
	public List<AvailabilityDto> getAvailabilities() {
		return availabilities;
	}
	public void setCourses(List<CourseDto> courses) {
		this.courses = courses;
	}
	public List<CourseDto> getCourses() {
		return courses;
	}
	public void setSessions(List<SessionDto> sessions) {
		this.sessions = sessions;
	}
	public List<SessionDto> getSessions() {
		return sessions;
	}
	public void setManagerId(int id) {
		managerId = id;
	}
	public int getManagerId() {
		return managerId;
	}

	public void setRates(List<RateDto> rates) {
		this.rates = rates;
	}
	public List<RateDto> getRates() {
		return rates;
	}

	public Set<QualificationDto> getQualification() {
	   return qualification;
	}
	
	public void setQualification(Set<QualificationDto> qualifications) {
	   this.qualification = qualifications;
	}

	
}
