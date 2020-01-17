package ca.mcgill.ecse321.backend.dto;

public class SingleSessionDto extends SessionDto{

  public SingleSessionDto(String course, int tutor, int student, int roomNumber,
      int duration, int id) {
    super(course, tutor, student, roomNumber, duration, id);
  }
}
