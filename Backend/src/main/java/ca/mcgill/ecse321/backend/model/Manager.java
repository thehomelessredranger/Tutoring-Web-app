package ca.mcgill.ecse321.backend.model;
import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

@Entity
public class Manager extends UserRole{

public Set<Tutor> getTutor() {
if (this.tutor == null) {
   this.tutor = new HashSet<Tutor>();
   }
   return this.tutor;
   }

@OneToMany(mappedBy = "manager")
private Set<Tutor> tutor;


@ManyToMany(mappedBy="manager")
private Set<Session> session;

public Set<Session> getSession() {
if (this.session == null) {
   this.session = new HashSet<Session>();
   }
   return this.session;
   }

}
