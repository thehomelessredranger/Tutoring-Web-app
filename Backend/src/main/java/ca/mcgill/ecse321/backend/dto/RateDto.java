package ca.mcgill.ecse321.backend.dto;

public class RateDto {
	private int rate;
	private int tutor;
	private String course;
	private int rateId;

	public RateDto(){
	}

	public RateDto(int rate, int tutor, String course, int id) {
		this.rate = rate;
		this.tutor = tutor;
		this.course = course;
		this.rateId = id;
	}

	public int getTutor() {
		return tutor;
	}

	public void setTutor(int tutor) {
		this.tutor = tutor;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getId() {
		return rateId;
	}

	public void setId(int id) {
		rateId = id;
	}
}
