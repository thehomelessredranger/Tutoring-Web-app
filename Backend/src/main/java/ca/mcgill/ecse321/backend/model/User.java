package ca.mcgill.ecse321.backend.model;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@Table(name="users")
public class User{
private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

@Id
private Integer idNumber;
private String password;

public void setPassword(String value) {
   this.password = value;
}

public String getPassword() {
   return this.password;
}

/**
 * <pre>
 *           1..1     0..1
 * User ------------------------- UserRole
 *           user        &gt;       userRole
 * </pre>
 */
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
@PrimaryKeyJoinColumn
private UserRole userRole;

public void setUserRole(UserRole value) {
   this.userRole = value;
}

public UserRole getUserRole() {
   return this.userRole;
}


public void setId(Integer value) {
this.idNumber = value;
   }

public Integer getId() {
return this.idNumber;
   }

}
