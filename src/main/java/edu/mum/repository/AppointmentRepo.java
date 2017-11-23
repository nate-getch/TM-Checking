
package edu.mum.repository;

import edu.mum.domain.Appointment;

import edu.mum.domain.Person;
import edu.mum.domain.Session;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {
    Appointment findById(int id);

    List<Appointment> findAppointmentsByPerson_Id(int id);

    //Appointment findAppointmentByPerson_Id(int id);

    public Appointment findAppointmentByIdAndPersonId(int appointmentId, int personId);

    //@Query("SELECT * FROM Appointment a WHERE a.sessionId = :sessionId")
    //public List<Appointment> countAppointment(Integer sessionId);
    int countAppointmentsBySession(Session s);

    //@Modifying
    //@Query(value = "insert into appointment (person_id,session_id) VALUES (:person_id, :session_id)", nativeQuery = true)
    //public void bookAppointment( @Param("person_id") int person_id, @Param("session_id") int session_id);

    public int countAppointmentsByPersonAndSession(Person p, Session s);

    public List<Appointment> findAppointmentsBySentEmailAndSession_DateGreaterThanEqual(Boolean flag, Date todayDate);

//    public List<Appointment> findAppointmentsBySession_DateEqualAndSession_StartTimeGreaterThan(Date todayDate, Time currentTime);

//    public List<Appointment> findAppointmentsBySession_DateGreaterThanEqualAndSession_StartTimeGreaterThan(Date todayDate, Time currentTime);

    @Modifying
    @Query("UPDATE Appointment SET sentEmail=:isSentEmail WHERE id=:appointmentId")
    void updateAppointmentIsSentEmail(@Param("appointmentId") Integer appointmentId, @Param("isSentEmail") boolean isSentEmail);
}