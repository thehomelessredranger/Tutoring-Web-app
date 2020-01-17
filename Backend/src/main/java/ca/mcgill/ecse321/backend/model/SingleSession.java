package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SingleSession extends Session{

@ManyToOne	
private Student student;

public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}


private RoomSmall room;

public RoomSmall getRoom() {
   return this.room;
}

public void setRoom(RoomSmall smallRoom) {
   this.room = smallRoom;
}


}
