package ca.mcgill.ecse321.backend.dto;

import java.util.List;

public class GroupSessionDto extends SessionDto{
	private int manager;
  private int groupSize;
//  private List<Integer> student;

  public GroupSessionDto(int manager, String course, int tutor, List<Integer> student, int roomNumber,
			int duration, int id, int groupSize) {
    super(course, tutor, student, roomNumber, duration, id);
    this.manager = manager;
    this.groupSize = groupSize;
  }

  public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}
	
  public void setGroupSize(int value) {
    this.groupSize = value;
  }

  public int getGroupSize() {
    return this.groupSize;
  }
}
