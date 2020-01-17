package ca.mcgill.ecse321.backend.dto;

public class QualificationDto {
	private int tutorId;
	private String school;
	private String degree; 
	private int startYear;
	private int endYear;
	private boolean onGoing;

	public QualificationDto(String school, String degree, int startYear, int endYear, boolean onGoing) {
		this.school = school;
		this.degree = degree;
		this.startYear = startYear;
		this.endYear = endYear;
		this.onGoing = onGoing;
	}


	public int getTutor() {
		return tutorId;
	}

	public void setTutor(int tutor) {
		this.tutorId = tutor;
	}

	public void setSchool(String value) {
		this.school = value;
	}

	public String getSchool() {
		return school;
	}

	public void setDegree(String value) {
		this.degree = value;
	}
	public String getDegree() {
		return degree;
	}


	public void setStartYear(int value) {
		this.startYear = value;
	}

	public int getStartYear() {
		return startYear;
	}


	public void setEndYear(int value) {
		this.endYear = value;
	}
	public int getEndYear() {
		return endYear;
	}


	public void setOnGoing(boolean value) {
		this.onGoing = value;
	}
	public boolean isOnGoing() {
		return onGoing;
	}
}
