package ca.mcgill.ecse321.backend.model;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class UserRole{

@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userRole")	
private User user;

public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}

@Id
private Integer idNumber;

public void setId(Integer value) {
this.idNumber = value;
   }

public Integer getId() {
return this.idNumber;
   }

}
