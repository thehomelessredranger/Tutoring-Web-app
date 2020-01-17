package ca.mcgill.ecse321.backend.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.backend.model.Course;
import ca.mcgill.ecse321.backend.model.Room;
import ca.mcgill.ecse321.backend.model.Session;
import ca.mcgill.ecse321.backend.model.Tutor;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer>{
	List<Session> findSessionByTutor(Tutor tutor);
	List<Session> findSessionByCourse(Course course);
	//List<Session> findSessionByStudent(Student student);
	//List<Session> findSessionByManager(Manager manager);
	Session findSessionBySessionId(Integer id);
	List<Session> findSessionByRoom(Room room);
	List<Session> findSessionByDate(LocalDateTime date);
	List<Session> findSessionByTutorAndSessionAccepted(Tutor tutor, boolean accepted);
}
