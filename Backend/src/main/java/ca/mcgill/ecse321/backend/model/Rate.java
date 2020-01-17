package ca.mcgill.ecse321.backend.model;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@SequenceGenerator(name="rt")
public class Rate{
private int rate;

public void setRate(int value) {
   this.rate = value;
}

public int getRate() {
   return this.rate;
}

@ManyToOne(optional=false)
private Tutor tutor;

public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

@ManyToOne(optional=false)
private Course course;

public Course getCourse() {
   return this.course;
}

public void setCourse(Course course) {
   this.course = course;
}

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rt")
private Integer rateId;

public void setId(Integer value) {
this.rateId = value;
   }

public Integer getId() {
return this.rateId;
   }

}
