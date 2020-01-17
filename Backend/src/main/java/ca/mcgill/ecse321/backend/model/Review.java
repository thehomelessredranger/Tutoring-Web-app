package ca.mcgill.ecse321.backend.model;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@SequenceGenerator(name="rev")
public class Review{

@ManyToOne(optional=false)
private Student student;

public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

private String feedback;

public void setFeedback(String value) {
   this.feedback = value;
}

public String getFeedback() {
   return this.feedback;
}


/**
 * <pre>
 *           1..1     1..1
 * Review ------------------------> Rating
 *           &lt;       rating
 * </pre>
 */
private Rating rating;

public void setRating(Rating value) {
   this.rating = value;
}

public Rating getRating() {
   return this.rating;
}

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rev")
private Integer reviewId;

public void setId(Integer value) {
this.reviewId = value;
   }

public Integer getId() {
return this.reviewId;
   }

}
