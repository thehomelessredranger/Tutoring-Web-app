package ca.mcgill.ecse321.backend.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Tutor extends UserRole{
public Set<Course> getCourse() {
if (this.course == null) {
   this.course = new HashSet<Course>();
   }
   return this.course;
   }

@OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
private Set<Review> review;

@OneToMany(mappedBy = "tutor")
private Set<Availability> availability;
@ManyToMany(mappedBy = "tutor")
private Set<Course> course;
@OneToMany(mappedBy = "tutor")
private Set<Session> session;
@OneToMany(mappedBy = "tutor")
private Set<Rate> rate;

private boolean isVerified;

public void setIsVerified(boolean value) {
   this.isVerified = value;
}

public boolean isIsVerified() {
   return this.isVerified;
}


@ManyToOne(optional=false)
private Manager manager;

public Manager getManager() {
   return this.manager;
}

public void setManager(Manager manager) {
   this.manager = manager;
}

@OneToMany(cascade={CascadeType.ALL})
private Set<Qualification> qualification;

public Set<Qualification> getQualification() {
if (this.qualification == null) {
   this.qualification = new HashSet<Qualification>();
   }
   return this.qualification;
   }

public void setQualification(Set<Qualification> qualifications) {
   this.qualification = qualifications;
}

public Set<Review> getReview() {
if (this.review == null) {
   this.review = new HashSet<Review>();
   }
   return this.review;
   }

public Set<Availability> getAvailability() {
if (this.availability == null) {
   this.availability = new HashSet<Availability>();
   }
   return this.availability;
   }

public Set<Rate> getRate() {
if (this.rate == null) {
   this.rate = new HashSet<Rate>();
   }
   return this.rate;
   }

public Set<Session> getSession() {
if (this.session == null) {
   this.session = new HashSet<Session>();
   }
   return this.session;
   }

}
