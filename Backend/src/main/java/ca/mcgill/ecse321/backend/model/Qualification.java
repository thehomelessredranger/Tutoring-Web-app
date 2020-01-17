package ca.mcgill.ecse321.backend.model;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import javax.persistence.Entity;

@Entity
@SequenceGenerator(name="qual")
public class Qualification{
	
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="qual")
private Integer qualificationId;
   
public void setQualificationId(Integer value) {
this.qualificationId = value;
    }

public Integer getQualificationId() {
return this.qualificationId;
    }

private String school;

public void setSchool(String value) {
   this.school = value;
}

public String getSchool() {
   return this.school;
}

private String degree;

public void setDegree(String value) {
   this.degree = value;
}

public String getDegree() {
   return this.degree;
}

private int startYear;

public void setStartYear(int value) {
   this.startYear = value;
}

public int getStartYear() {
   return this.startYear;
}

private int endYear;

public void setEndYear(int value) {
   this.endYear = value;
}

public int getEndYear() {
   return this.endYear;
}

private boolean onGoing;

public void setOnGoing(boolean value) {
   this.onGoing = value;
}

public boolean isOnGoing() {
   return this.onGoing;
}

@ManyToOne	
private Tutor tutor;

public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor Tutor) {
   this.tutor = Tutor;
}

}
