package ca.mcgill.ecse321.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import javax.persistence.JoinColumn;

@Entity
public class Student extends UserRole{
@ManyToMany(cascade = { CascadeType.ALL })
@JoinTable( name = "Student_Session", 
				joinColumns = { @JoinColumn(name = "student_id") }, 
				    inverseJoinColumns = { @JoinColumn(name = "session_id") } )
private Set<Session> session;

public Set<Session> getSession() {
   return this.session;
}


public Set<Review> getReview() {
if (this.review == null) {
   this.review = new HashSet<Review>();
   }
   return this.review;
   }

@OneToMany(mappedBy = "student")
private Set<Review> review;
/**
 * <pre>
 *           1..1     1..1
 * Student ------------------------> Rating
 *           &lt;       averageRating
 * </pre>
 */
private Rating averageRating;

public void setAverageRating(Rating value) {
   this.averageRating = value;
}

public Rating getAverageRating() {
   return this.averageRating;
}


}
