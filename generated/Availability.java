import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class Availability{
private Tutor tutor;

@ManyToOne(optional=false)
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
private Day dayOfWeek;

@OneToOne(optional=false)
public Day getDayOfWeek() {
   return this.dayOfWeek;
}

public void setDayOfWeek(Day dayOfWeek) {
   this.dayOfWeek = dayOfWeek;
}

private int id;

public void setId(int value) {
this.id = value;
    }
public int getId() {
return this.id;
       }
   }
