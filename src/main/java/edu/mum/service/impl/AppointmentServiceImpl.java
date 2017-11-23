package edu.mum.service.impl;

import edu.mum.domain.Appointment;
import edu.mum.domain.Person;
import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import edu.mum.domain.Session;
import edu.mum.repository.AppointmentRepo;
import edu.mum.repository.SessionRepo;
import edu.mum.service.AppointmentService;
import edu.mum.service.EmailService;
import edu.mum.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    PersonService personService;

    @Autowired
    EmailService emailService;
    //private List<Appointment> ;

    public List<Appointment> findAll() {
        return (List<Appointment>) appointmentRepo.findAll();
    }

    public List<Appointment> getMyAppointments() {
        Person p = null;
        p = (Person) personService.getCurrentAuthenticatedUser();

        return (List<Appointment>) appointmentRepo.findAppointmentsByPerson_Id(p.getId());
        // return (List<Appointment>) appointmentRepo.findAll();
    }

    public void updateMyAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    public Appointment bookAppointment(Session session) {
        if (checkIfUserBookedAppointment(session) == true) {
            return null;
        }
        Person person = (Person) personService.getCurrentAuthenticatedUser();
        Appointment appointment = new Appointment(person, session);
        sessionRepo.increaseAvailableSits(appointment.getSession().getId());
        //appointmentRepo.bookAppointment(person.getId(), session.getId());
        return appointmentRepo.save(appointment);
    }

    @Override
    public void delete(Integer appointmentId) {
        boolean isAdmin = false;
        Person currentPerson = personService.getCurrentAuthenticatedUser();
        for (PersonRole role : currentPerson.getPersonRoles()) {
            if (role.getRole().equals(Role.ROLE_ADMIN))
                isAdmin = true;
        }
        // if its admin OR owner of respective appointment, delete is allowed
        if (isAdmin || appointmentRepo.findAppointmentByIdAndPersonId(appointmentId, currentPerson.getId()) != null) {
            Appointment appointment = appointmentRepo.findById(appointmentId);
            sessionRepo.decreaseAvailableSits(appointment.getSession().getId());
            appointmentRepo.delete(appointmentId);
        }

//			if(!isAdmin) {
//				if(currentPerson.getId().equals(appointmentRepo.findAppointmentsByPerson_Id(appointmentId))) {
//					
//				}
//			}

        //appointmentRepo.delete(appointmentId);

    }

    @Override
    public Appointment findOne(int id) {
        return appointmentRepo.findOne(id);
    }

    @Override
    public boolean checkIfUserBookedAppointment(Session session) {
        Person person = (Person) personService.getCurrentAuthenticatedUser();
        int count = appointmentRepo.countAppointmentsByPersonAndSession(person, session);
        //checkIfUserBookedAppointment
        if (count > 0)
            return true;
        return false;
    }

    @Override
    public int countAppointment(Session s) {
        return appointmentRepo.countAppointmentsBySession(s);
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(fixedDelay = 1000*10)
    public void appointmentReminder() {
        List<Appointment> appointments = (List<Appointment>) findActiveAppoitnments();
        for (Appointment appointment : appointments) {
            if (!appointment.isSentEmail()) {
                Session session = appointment.getSession();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTime(session.getDate(), session.getStartTime()));
                calendar.add(Calendar.HOUR_OF_DAY, -36);
                Date checkingDate = calendar.getTime();
                Date now = new Date();
                if (now.after(checkingDate)) {
                    sendEmail(appointment);
                    appointmentRepo.updateAppointmentIsSentEmail(appointment.getId(), true);
                }
            }
        }
    }

    public Date dateTime(Date date, Date time) {
        return new Date(
                date.getYear(), date.getMonth(), date.getDate(),
                time.getHours(), time.getMinutes(), time.getSeconds()
        );
    }

    public void sendEmail(Appointment appointment) {
        Map<String, Object> model = new HashMap<>();
        model.put("person", appointment.getPerson());
        model.put("session", appointment.getSession());
        emailService.send("no-reply@earthTeam.com", appointment.getPerson().getEmail(), "Dear " + appointment.getPerson().getFirstName() + " you have appointent to remind", "appointmentReminder", model);

    }

    @Override
    public List<Appointment> findActiveAppoitnments(){
        //return (List<Appointment>) appointmentRepo.findAppointmentsBySession_DateGreaterThan(new Date());
        //Get current date time
        //LocalDateTime
        //LocalTime currentTime = LocalDateTime.now().toLocalTime();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        //String currentTime = now.format(formatter);
        Date today = new Date();
        Boolean userHasReceivedNotificationEmail = false;
        List<Appointment> appointments = (List<Appointment>) appointmentRepo.findAppointmentsBySentEmailAndSession_DateGreaterThanEqual(userHasReceivedNotificationEmail, today);
       /* appointments.stream()
                .filter( (a) ->
                         {
                             // if its today and the time has passed, remove from list
                            if(a.getSession().getDate().compareTo(today) == 0){ // && a.getSession().getStartTime().getTime() < today.getTime()  ){
                                System.out.println("Session Date" + a.getSession().getDate());
                                appointments.remove(a);
                            }

                             return true;
                          }); */

        return appointments;
    }
}
