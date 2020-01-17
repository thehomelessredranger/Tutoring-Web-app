package ca.mcgill.ecse321.backend.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Room{

	@Id
	private Integer roomNumber;

	public Integer getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(Integer number) {
		this.roomNumber = number;
	}

}
