package ca.mcgill.ecse321.backend.dto;

import ca.mcgill.ecse321.backend.model.Day;

public class AvailabilityDto {
	private int tutor;
	private int startTime;
	private int endTime;
	private Day day;
	private int availabilityId;

	public AvailabilityDto(int tutor, int startTime, int endTime, Day day, int id) {
		this.tutor = tutor;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
		this.availabilityId = id;
	}

	public int getTutor() {
		return tutor;
	}

	public void setTutor(int tutor) {
		this.tutor = tutor;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public int getId() {
		return availabilityId;
	}

	public void setId(int id) {
		availabilityId = id;
	}


}
