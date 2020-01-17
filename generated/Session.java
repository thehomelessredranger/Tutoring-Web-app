import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;

@Entity
public abstract class Session{
private SystemManager systemManager;

@ManyToOne(optional=false)
public SystemManager getSystemManager() {
   return this.systemManager;
}

public void setSystemManager(SystemManager systemManager) {
   this.systemManager = systemManager;
}

@ManyToMany(mappedBy="session")
public Set<Course> getCourse() {
   return this.course;
}

private Manager manager;

@ManyToOne
public Manager getManager() {
   return this.manager;
}

public void setManager(Manager manager) {
   this.manager = manager;
}

private Set<Course> course;

public void setCourse(Set<Course> courses) {
   this.course = courses;
}

private Tutor tutor;

@ManyToOne(optional=false)
public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private Student student;

@ManyToOne(optional=false)
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

private int roomNumber;

public void setRoomNumber(int value) {
this.roomNumber = value;
    }
public int getRoomNumber() {
return this.roomNumber;
    }
private int sessionDuration;

public void setSessionDuration(int value) {
this.sessionDuration = value;
    }
public int getSessionDuration() {
return this.sessionDuration;
    }
private Room room;

@OneToOne(optional=false)
public Room getRoom() {
   return this.room;
}

public void setRoom(Room room) {
   this.room = room;
}

private String time;

public void setTime(String value) {
this.time = value;
    }
public String getTime() {
return this.time;
    }
private int id;

public void setId(int value) {
this.id = value;
    }
public int getId() {
return this.id;
    }
private LocalDateTime date;

@OneToOne(optional=false)
public LocalDateTime getDate() {
   return this.date;
}

public void setDate(LocalDateTime date) {
   this.date = date;
}

private boolean sessionAccepted;

public void setSessionAccepted(boolean value) {
this.sessionAccepted = value;
    }
public boolean isSessionAccepted() {
return this.sessionAccepted;
       }
   }
