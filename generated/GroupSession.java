import javax.persistence.Entity;

@Entity
public class GroupSession extends Session{
private int groupSize;
   
   public void setGroupSize(int value) {
this.groupSize = value;
    }
public int getGroupSize() {
return this.groupSize;
       }
   }
