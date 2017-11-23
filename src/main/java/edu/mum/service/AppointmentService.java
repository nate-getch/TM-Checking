package edu.mum.service;

import edu.mum.domain.Appointment;
import edu.mum.domain.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface AppointmentService {

    public List<Appointment> findAll();

    public List<Appointment> findActiveAppoitnments();

    @Transactional
    public Appointment save(Appointment appointment);

    @Transactional
    public Appointment bookAppointment(Session s);

    public boolean checkIfUserBookedAppointment(Session s);

    public List<Appointment> getMyAppointments();

    public Appointment findOne(int id);

    @Transactional
    public void updateMyAppointment(Appointment appointment);

    @Transactional
    public void delete(Integer appointmentId);

    public int countAppointment(Session s);

    @Transactional
    void appointmentReminder();
}
