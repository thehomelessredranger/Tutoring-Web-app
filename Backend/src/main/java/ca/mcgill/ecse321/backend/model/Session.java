package ca.mcgill.ecse321.backend.model;
import javax.persistence.SequenceGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@SequenceGenerator(name="sess")
public abstract class Session{

@ManyToOne(optional=false)
private Course course;

public Course getCourse() {
   return this.course;
}

public void setCourse(Course course) {
   this.course = course;
}

@ManyToOne(optional=false)
private Tutor tutor;

public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private int sessionDuration;

public void setSessionDuration(int value) {
   this.sessionDuration = value;
}

public int getSessionDuration() {
   return this.sessionDuration;
}

@ManyToOne(optional=false)
private Manager manager;

public Manager getManager() {
   return this.manager;
}

public void setManager(Manager manager) {
   this.manager = manager;
}

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sess")
private Integer sessionId;
/**
 * <pre>
 *           1..1     1..1
 * Session ------------------------> LocalDateTime
 *           &lt;       date
 * </pre>
 */
private LocalDateTime date;

public void setDate(LocalDateTime value) {
   this.date = value;
}

public LocalDateTime getDate() {
   return this.date;
}

private boolean sessionAccepted;

public void setSessionAccepted(boolean value) {
   this.sessionAccepted = value;
}

public boolean isSessionAccepted() {
   return this.sessionAccepted;
}


public void setId(Integer value) {
this.sessionId = value;
   }

public Integer getId() {
return this.sessionId;
   }

@ManyToOne(optional=false)
private Room room;

public Room getRoom() {
   return this.room;
}

}
