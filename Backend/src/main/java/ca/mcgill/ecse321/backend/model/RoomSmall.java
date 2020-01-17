package ca.mcgill.ecse321.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class RoomSmall extends Room{
	
@OneToMany(mappedBy = "room")	
private Set<SingleSession> sessions;

public Set<SingleSession> getSingleSession() {
	if (this.sessions == null) {
		this.sessions = new HashSet<SingleSession>();
	}
		return this.sessions;
	}


}
