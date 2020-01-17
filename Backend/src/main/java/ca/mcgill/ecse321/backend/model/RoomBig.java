package ca.mcgill.ecse321.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class RoomBig extends Room{
	
@OneToMany(mappedBy = "room")
private Set<GroupSession> sessions;

public Set<GroupSession> getGroupSession() {
	if (this.sessions == null) {
		this.sessions = new HashSet<GroupSession>();
	}
		return this.sessions;
	}


}
