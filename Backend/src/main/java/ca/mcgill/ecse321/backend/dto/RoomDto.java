package ca.mcgill.ecse321.backend.dto;

import java.util.List;


public class RoomDto {

	private int roomNum;
	private List<Integer> sessions;
	
	public RoomDto(int room) {
		this.roomNum = room;
	}
	
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int room) {
		this.roomNum = room;
	}


	public List<Integer> getSessions() {
	
		return this.sessions;
	}

}
