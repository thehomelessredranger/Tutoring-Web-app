import javax.persistence.OneToOne;
import javax.persistence.Entity;

@Entity
public class User{
private String name;
   
   public void setName(String value) {
this.name = value;
    }
public String getName() {
return this.name;
    }
private String password;

public void setPassword(String value) {
this.password = value;
    }
public String getPassword() {
return this.password;
    }
private UserRole userRole;

@OneToOne(mappedBy="user")
public UserRole getUserRole() {
   return this.userRole;
}

public void setUserRole(UserRole userRole) {
   this.userRole = userRole;
}
private int id;

public void setId(int value) {
this.id = value;
    }
public int getId() {
return this.id;
       }
   }
