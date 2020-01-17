package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class StudentDto extends UserRoleDto{

	List<ReviewDto> reviews;
	List<SessionDto> sessions;
	int averageRating;


	public StudentDto(int id, int userId, int averageRating) {
		super(id, userId);
		this.averageRating = averageRating;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}
	public List<ReviewDto> getReviews() {
		return reviews;
	}
	public void setSessions(List<SessionDto> sessions) {
		this.sessions = sessions;
	}
	public List<SessionDto> getSessions() {
		return sessions;
	}
	public void setAverageRating(int r) {
		averageRating = r;
	}
	public int getAverageRating() {
		return averageRating;
	}
}
