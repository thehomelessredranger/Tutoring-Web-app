import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Course{
private SystemManager systemManager;

@ManyToOne(optional=false)
public SystemManager getSystemManager() {
   return this.systemManager;
}

public void setSystemManager(SystemManager systemManager) {
   this.systemManager = systemManager;
}

@ManyToMany
public Set<Session> getSession() {
   return this.session;
}

@ManyToMany
public Set<Tutor> getTutor() {
   return this.tutor;
}

private String subject;

public void setSubject(String value) {
this.subject = value;
    }
public String getSubject() {
return this.subject;
    }
private int courseNumber;

public void setCourseNumber(int value) {
this.courseNumber = value;
    }
public int getCourseNumber() {
return this.courseNumber;
    }
private String courseName;

public void setCourseName(String value) {
this.courseName = value;
    }
public String getCourseName() {
return this.courseName;
    }
private Set<Session> session;

public void setSession(Set<Session> sessions) {
   this.session = sessions;
}

private Set<Tutor> tutor;

public void setTutor(Set<Tutor> tutors) {
   this.tutor = tutors;
}

}
