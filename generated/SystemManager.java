import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class SystemManager{
private Set<Course> course;

@OneToMany(mappedBy="systemManager", cascade={CascadeType.ALL})
public Set<Course> getCourse() {
   return this.course;
}

public void setCourse(Set<Course> courses) {
   this.course = courses;
}

private Set<Session> session;

@OneToMany(mappedBy="systemManager", cascade={CascadeType.ALL})
public Set<Session> getSession() {
   return this.session;
}

public void setSession(Set<Session> sessions) {
   this.session = sessions;
}

private Manager manager;

public void setManager(Manager value) {
this.manager = value;
    }
public Manager getManager() {
return this.manager;
       }
   }
