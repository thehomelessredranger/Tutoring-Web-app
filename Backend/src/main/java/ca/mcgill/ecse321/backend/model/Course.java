package ca.mcgill.ecse321.backend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import java.util.HashSet;

@Entity
public class Course{
	
public Set<Tutor> getTutor ()
{
if (this.tutor == null) {
   this.tutor = new HashSet<Tutor>();
   }
   return this.tutor;
   }

public Set<Session> getSession() {
if (this.session == null) {
   this.session = new HashSet<Session>();
   }
   return this.session;
   }

@ManyToMany(cascade = {CascadeType.ALL})
@JoinTable(name = "Course_Tutor", joinColumns = {@JoinColumn(name = "course_id")
}, inverseJoinColumns = {@JoinColumn(name = "tutor_id")
})
private Set<Tutor> tutor;

private String subject;

public void setSubject(String value) {
   this.subject = value;
}

public String getSubject() {
   return this.subject;
}

private int courseNumber;

public void setCourseNumber(int value) {
   this.courseNumber = value;
}

public int getCourseNumber() {
   return this.courseNumber;
}

private String courseName;

public void setCourseName(String value) {
   this.courseName = value;
}

public String getCourseName() {
   return this.courseName;
}

@OneToMany(mappedBy = "course")
private Set<Session> session;

@OneToMany(mappedBy = "course")
private Set<Rate> rate;

@Id
private String courseId;

public Set<Rate> getRate() {
if (this.rate == null) {
   this.rate = new HashSet<Rate>();
   }
   return this.rate;
   }

public String getCourseId() {
	if(courseId == null) {
this.courseId = subject + courseNumber;
   return this.courseId;
   }
	else return this.courseId;
}

}
