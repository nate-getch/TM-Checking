package edu.mum.aop;

import edu.mum.domain.Appointment;
import edu.mum.domain.Session;
import edu.mum.service.AppointmentService;
import edu.mum.service.EmailService;
import edu.mum.service.SessionService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
public class SendingEmail {

    private final EmailService emailService;

    @Autowired
    private AppointmentService appService;
    
    @Autowired
    private SessionService sessionService;

    @Autowired
    public SendingEmail(EmailService emailService) {
        this.emailService = emailService;
    }

    @Pointcut("execution(* edu.mum.service.AppointmentService.save(..))")
	public void save() {
	}

	@Pointcut("execution(* edu.mum.service.AppointmentService.updateMyAppointment(..))")
	public void update() {
    }

    @Pointcut("execution(* edu.mum.service.AppointmentService.bookAppointment(..))")
	public void book() {
    }

	@Around("book()")
	public Appointment sendEmail(ProceedingJoinPoint joinPoint){
        Object[] objects = joinPoint.getArgs();
		Map<String, Object> model = new HashMap<>();
        try {
            Appointment appointment = (Appointment) joinPoint.proceed(objects);
            Session session = appointment.getSession();
            model.put("person", appointment.getPerson());
            model.put("councelor", session.getPerson());
            model.put("session", session);
            emailService.send("no-reply@earthTeam.com", appointment.getPerson().getEmail(), "Thank you for your booking", "bookConfirmation", model);
            emailService.send("no-reply@earthTeam.com", session.getPerson().getEmail(), "Dear " + session.getPerson().getFirstName() + " your session has one more customer", "councelorReminder", model);
            return appointment;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
	    return null;
    }

	@Around("execution(* edu.mum.service.SessionService.save(..)) && args(session)")
	public Object sendNotificationWhileChangingSession(ProceedingJoinPoint joinpoint, Session session)
			throws Throwable {
		boolean sendAddConfirm = false;
		if(session.getId() == 0) {
			sendAddConfirm = true;
		}
		Object obj = joinpoint.proceed();
		List<Appointment> appmts = appService.findAll().stream().filter(y -> y.getSession().getId() == session.getId())
				.collect(Collectors.toList());
		if (appmts != null && !appmts.isEmpty()) {
			String template = "editSessionConfirmation";
			Map<String, Object> model = new HashMap<>();
			model.put("session", session);
			model.put("counselor", session.getPerson());
			for (Appointment apptmnt : appmts) {
				model.put("person", apptmnt.getPerson());
				String title = "Hello Mr/Mrs " + apptmnt.getPerson().getFirstName() + ", Session is changed";
				sendEmail(apptmnt.getPerson().getEmail(), title, template, model);
			}
		}
		if(sendAddConfirm) {
			Map<String, Object> model = new HashMap<>();
			model.put("session", session);
			model.put("counselor", session.getPerson());
			String title = "Hello Mr/Mrs " + session.getPerson().getFirstName() + ", Your leading new Session is created";
			sendEmail(session.getPerson().getEmail(), title, "addSessionConfirmation", model);
		}
		return obj;
	}

	@Around("execution(* edu.mum.service.SessionService.removeById(..)) && args(id)")
	public Object sendNotificationWhileChangingSession(ProceedingJoinPoint joinpoint, int id) throws Throwable {
		Map<String, Object> model = new HashMap<>();
		model.put("sessionId", id);
		model.put("person", sessionService.get(id).getPerson());
		String template = "removeSessionConfirmation";
		String title = "Hello Mr/Mrs " + sessionService.get(id).getPerson().getFirstName() + ", Your leading Session " + id
				+ " is removed";
		sendEmail(sessionService.get(id).getPerson().getEmail(), title, template, model);
		
		List<Appointment> list = appService.findAll().stream().filter(y->y.getSession().getId() == id).collect(Collectors.toList());
		Object obj = joinpoint.proceed();
		
		if (list != null && !list.isEmpty()) {
			for (Appointment apptmnt : list) {
				model.put("person", apptmnt.getPerson());
				title = "Hello Mr/Mrs " + apptmnt.getPerson().getFirstName() + ", Session " + id
						+ " is removed";
				sendEmail(apptmnt.getPerson().getEmail(), title, template, model);
			}
		}
		return obj;
	}

	public void sendEmail(String toEmail, String title, String template, Map<String, Object> model) {
		emailService.send("no-reply@earthTeam.com", toEmail, title, template, model);
	}
}
