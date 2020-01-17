package ca.mcgill.ecse321.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseDto {
	private List<Integer> tutor;
	private String subject;
	private int number;
	private String name;
	private String courseId;
	List<RateDto> rates;

	public CourseDto(List<Integer> tutor, String subject, int number, String name, String courseId, List<RateDto> rates) {
		this.tutor = tutor;
		this.subject = subject;
		this.number = number;
		this.name = name;
		this.courseId = courseId;
		this.rates = rates;
	}

	public List <Integer> getTutor() {
		return tutor;
	}

	public void setTutor(List <Integer> tutor) {
		this.tutor = tutor;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String id) {
		courseId = id;
	}
	public List<RateDto> getRates() {
		if (this.rates == null) {
			this.rates = new ArrayList<>();
		}
		return this.rates;
	}
	public double getAverageRate() {
		if (rates.size() == 0) {
			return 0;
		}
		double ave = 0;
		for(RateDto rate:rates) {
			ave+=rate.getRate();
		}
		ave/=rates.size();
		return ave;
	}

}
