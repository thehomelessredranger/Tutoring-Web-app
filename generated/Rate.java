/**
    * <pre>
    *           1..1     1..1
    * Rate ------------------------> Course
    *           &gt;       course
    * </pre>
    */import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Rate{
   private Tutor tutor;
   
   @ManyToOne(optional=false)
   public Tutor getTutor() {
      return this.tutor;
   }
   
   public void setTutor(Tutor tutor) {
      this.tutor = tutor;
   }
   
   private Course course;
   
   @OneToOne(optional=false)
   public Course getCourse() {
      return this.course;
   }
   
   public void setCourse(Course course) {
      this.course = course;
   }
   
   private Set<Integer> rate;

public void setRate(Set<Integer> value) {
    this.rate = value;
}
public Set<Integer> getRate() {
    return this.rate;
}
private int id;

public void setId(int value) {
    this.id = value;
}
public int getId() {
    return this.id;
}
}
