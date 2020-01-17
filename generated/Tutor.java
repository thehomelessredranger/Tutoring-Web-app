import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Tutor extends UserRole{
@OneToMany(mappedBy="tutor")
public Set<Availability> getAvailability() {
   return this.availability;
}

@ManyToMany(mappedBy="tutor")
public Set<Course> getCourse() {
   return this.course;
}

@OneToMany(mappedBy="tutor")
public Set<Review> getReview() {
   return this.review;
}

@OneToMany(mappedBy="tutor")
public Set<Session> getSession() {
   return this.session;
}

private Set<Review> review;

public void setReview(Set<Review> reviews) {
   this.review = reviews;
}

private Set<Availability> availability;

public void setAvailability(Set<Availability> availabilitys) {
   this.availability = availabilitys;
}

private Set<Session> session;

public void setSession(Set<Session> sessions) {
   this.session = sessions;
}

/**
 * <pre>
 *           1..*     1..1
 * Tutor ------------------------- Manager
 *           &gt;       manager
 * </pre>
 */private boolean isVerified;

public void setIsVerified(boolean value) {
this.isVerified = value;
    }
public boolean isIsVerified() {
return this.isVerified;
    }
private Manager manager;

@OneToOne(optional=false)
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

}
