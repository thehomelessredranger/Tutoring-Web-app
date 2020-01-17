package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class ManagerDto extends UserRoleDto {

	List<SessionDto> sessions;
	List<TutorDto> tutors;

	public ManagerDto(int id, int userId) {
		super(id, userId);
	}

	public void setSessions(List<SessionDto> sessions) {
		this.sessions = sessions;
	}

	public List<SessionDto> getSessions() {
		return sessions;
	}

	public void setTutors(List<TutorDto> tutors) {
		this.tutors = tutors;
	}

	public List<TutorDto> getTutors() {
		return tutors;
	}
}
