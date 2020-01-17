package ca.mcgill.ecse321.backend.model;

import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;



@Entity
@SequenceGenerator(name="avl", initialValue=1, allocationSize=1)
public class Availability{

@ManyToOne(optional=false)
private Tutor tutor;

public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private int startTime;

public void setStartTime(int value) {
   this.startTime = value;
}

public int getStartTime() {
   return this.startTime;
}

private int endTime;

public void setEndTime(int value) {
   this.endTime = value;
}

public int getEndTime() {
   return this.endTime;
}

/**
 * <pre>
 *           1..1     1..1
 * Availability ------------------------> Day
 *           &lt;       dayOfWeek
 * </pre>
 */
private Day dayOfWeek;

public void setDayOfWeek(Day value) {
   this.dayOfWeek = value;
}

public Day getDayOfWeek() {
   return this.dayOfWeek;
}

@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avl")
private Integer availabilityId;

public void setId(Integer value) {
this.availabilityId = value;
   }

public Integer getId() {
return this.availabilityId;
   }

}
