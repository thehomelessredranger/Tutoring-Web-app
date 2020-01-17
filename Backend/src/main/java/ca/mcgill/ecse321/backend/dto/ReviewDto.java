package ca.mcgill.ecse321.backend.dto;

public class ReviewDto {

	private String feedback;
	private int rating;
	private int id;
	private int student;

	public ReviewDto() {
	}

	public ReviewDto(int student, int rating, int id) {
		this(student, rating, "", id);
	}

	public ReviewDto(int student, int rating, String feedback, int id) {
		this.student = student;
		this.rating = rating;
		this.feedback = feedback;
		this.id = id;
	}

	public int getRating() {
		return rating;
	}
	public String getFeedback() {
		return feedback;
	}
	public int getStudent() {
		return student;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
