package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class SessionDto {
	private String course;
	private int tutor;
	private int roomnumber;
	private int duration;
	private int id;
	private List<Integer> students;

	public SessionDto(String course, int tutor, List<Integer> students, int roomnumber,
			int duration, int id) {
		// super();
		this.course = course;
		this.tutor = tutor;
		this.roomnumber = roomnumber;
		this.duration = duration;
		this.id = id;
		this.students = students;
	}
	
	public SessionDto(String course, int tutor, int student, int roomnumber,
			int duration, int id) {
		// super();
		this.course = course;
		this.tutor = tutor;
		this.roomnumber = roomnumber;
		this.duration = duration;
		this.id = id;
		this.setStudent(student);
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getTutor() {
		return tutor;
	}

	public void setTutor(int tutor) {
		this.tutor = tutor;
	}

	public int getRoomnumber() {
		return roomnumber;
	}

	public void setRoomnumber(int roomnumber) {
		this.roomnumber = roomnumber;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getStudent() {
		return this.students;
	}
	public void setStudent(int aStudent) {
		students.clear();
		this.students.add(aStudent);
	}
	
}
