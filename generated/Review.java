import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class Review{
private Student student;

@ManyToOne(optional=false)
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

private Tutor tutor;

@ManyToOne(optional=false)
public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private String feedback;

public void setFeedback(String value) {
this.feedback = value;
    }
public String getFeedback() {
return this.feedback;
    }
private Rating rating;

@OneToOne(optional=false)
public Rating getRating() {
   return this.rating;
}

public void setRating(Rating rating) {
   this.rating = rating;
}

private int id;

public void setId(int value) {
this.id = value;
    }
public int getId() {
return this.id;
       }
   }
