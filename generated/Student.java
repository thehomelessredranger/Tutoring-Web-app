import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Student extends UserRole{
@OneToMany(mappedBy="student")
public Set<Review> getReview() {
   return this.review;
}

@OneToMany(mappedBy="student")
public Set<Session> getSession() {
   return this.session;
}

private Set<Review> review;

public void setReview(Set<Review> reviews) {
   this.review = reviews;
}

private Set<Session> session;

public void setSession(Set<Session> sessions) {
   this.session = sessions;
}

private Rating averageRating;

@OneToOne(optional=false)
public Rating getAverageRating() {
   return this.averageRating;
}

public void setAverageRating(Rating averageRating) {
   this.averageRating = averageRating;
}

}
