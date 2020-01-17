package ca.mcgill.ecse321.backend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer>{
	
	Room findByRoomNumber(Integer number);
	

}