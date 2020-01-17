import javax.persistence.OneToOne;
import javax.persistence.Entity;

@Entity
public abstract class UserRole{
private User user;

@OneToOne(optional=false)
public User getUser() {
   return this.user;
}

public void setUser(User user) {
   this.user = user;
}

}
