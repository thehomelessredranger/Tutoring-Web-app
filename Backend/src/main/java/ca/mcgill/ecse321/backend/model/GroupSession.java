package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class GroupSession extends Session{
private int groupSize;

public void setGroupSize(int value) {
   this.groupSize = value;
}

public int getGroupSize() {
   return this.groupSize;
}

@ManyToMany
private Set<Student> student;

public Set<Student> getStudent() {
   return this.student;
}

public void setStudent(Set<Student> students) {
   this.student = students;
}


private RoomBig room;

public RoomBig getRoom() {
   return this.room;
}

public void setRoom(RoomBig bigRoom) {
   this.room = bigRoom;
}


}
