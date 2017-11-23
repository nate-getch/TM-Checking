package edu.mum.controller;

import edu.mum.domain.Appointment;
import edu.mum.domain.Location;
import edu.mum.domain.Person;
import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import edu.mum.domain.Session;
import edu.mum.service.AppointmentService;
import edu.mum.service.PersonService;
import edu.mum.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointment")
@SessionAttributes({"rerdirectUrl"})
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    SessionService sessionService;

    @Autowired
    PersonService personService;

    @RequestMapping(value = {"", "/"})
    public String listAppointments(Model model) {
        model.addAttribute("header_title", "My Appointment List");
        model.addAttribute("rerdirectUrl", "/appointment");
        model.addAttribute("appointmentList", appointmentService.getMyAppointments());
        return "appointment/list";
    }

    // testing code
    /*
	@RequestMapping(value = { "/add" })
	public String addAppointment(Model model) {
		model.addAttribute("header_title", "My Appointment List");
		// test data, need to accept input from view
		Person p = null;
		try {
			p = (Person) personService.getCurrentAuthenticatedUser();

			Location location = new Location("1234", "34");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date yourDate = sdf.parse("2017-10-01 05:30:00");


		} catch (Exception e) {
			System.out.println(e);
		}
		return "appointment/add";
	} */

    @RequestMapping(value = {"/book/{id}"})
    public String bookAppointment(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("header_title", "My Appointment List");

        try {
            Session session = sessionService.get(id);

            if (session.getNumberofsits() == appointmentService.countAppointment(session)) {
                redirectAttributes.addFlashAttribute("errorMessage", "The number of sits is full.");
                return "redirect:/session/";
            } else if (appointmentService.bookAppointment(session) != null) {
                redirectAttributes.addFlashAttribute("successMessage", "Appointment booking successful. ");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Unable to book - You have already signed up for this Appointment.");
                return "redirect:/session/";
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return "redirect:/appointment";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("appointment", appointmentService.findOne(id));
        return "appointmentDetail";
    }

    // @RequestMapping(value="/update", method=RequestMethod.POST)
    // public String update(Appointment appointment, @RequestParam("id") Integer id,
    // Model model) {
    //
    // Date sessiondate = appointmentService.findOne(id).getSession().getDate();
    //
    // Calendar cal = Calendar.getInstance();
    // cal.setTime(sessiondate);
    // cal.add(Calendar.DATE, -2);
    // Date dateBefore2Days = cal.getTime();
    //
    // if(appointmentService.findOne(id).getSession().getDate().after(dateBefore2Days))
    // {
    // return "redirect:/appointment";
    // }
    // appointmentService.updateMyAppointment(appointment);
    // return "redirect:/updateApp";
    // }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer appointmentId, Model model, RedirectAttributes redirect) {

        String rerdirectUrl = (String) model.asMap().get("rerdirectUrl");

        Date sessiondate = dateTime(appointmentService.findOne(appointmentId).getSession().getDate(), appointmentService.findOne(appointmentId).getSession().getStartTime());
        boolean isAdmin = false;
        Person currentPerson = personService.getCurrentAuthenticatedUser();
        for (PersonRole role : currentPerson.getPersonRoles()) {
            if (role.getRole().equals(Role.ROLE_ADMIN))
                isAdmin = true;
        }

        if (isAdmin) {
            if (appointmentService.findOne(appointmentId).getSession().getDate().before(new Date())) {
                redirect.addFlashAttribute("errorMessage", "Unable to cancel because it is past due date ");
                return "redirect:" + rerdirectUrl;
            } else {
                appointmentService.delete(appointmentId);
                return "redirect:" + rerdirectUrl;
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(sessiondate);
        cal.add(Calendar.HOUR, -48);
        Date dateBefore48hours = cal.getTime();

        Calendar currentdate = Calendar.getInstance();
        Date currenttime = currentdate.getTime();
        //new Date().getHours()

        if ((currenttime).after(dateBefore48hours)) {
            redirect.addFlashAttribute("errorMessage", "Unable to cancel because the appointment is in less than 48 hours");
            return "redirect:" + rerdirectUrl;
        }

        System.out.println("***************************");
        System.out.println(dateBefore48hours);
        System.out.println(currenttime);
        System.out.println("***************************");

        appointmentService.delete(appointmentId);
        return "redirect:" + rerdirectUrl;
    }

    @SuppressWarnings("deprecation")
    public Date dateTime(Date date, Date time) {
        return new Date(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(),
                time.getSeconds());
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("header_title", "Manage Appointments");
        model.addAttribute("rerdirectUrl", "/appointment/manage");
        model.addAttribute("appointmentList", appointmentService.findAll());
        return "appointment/list";
    }

    @RequestMapping(value = "/counselor", method = RequestMethod.GET)
    public String getAppointments(Model model) {
        Person person = (Person) personService.getCurrentAuthenticatedUser();
        model.addAttribute("header_title", "Counselor Appointments");
        model.addAttribute("rerdirectUrl", "/appointment/counselor");
        model.addAttribute("appointmentList", appointmentService.findAll().stream()
                .filter(s -> s.getSession().getPerson().getId().equals(person.getId())).collect(Collectors.toList()));
        return "appointment/list";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        Person person = (Person) personService.getCurrentAuthenticatedUser();
        model.addAttribute("header_title", "Counselor Appointments");
        model.addAttribute("rerdirectUrl", "/appointment/counselor");
        model.addAttribute("appointmentList", appointmentService.findActiveAppoitnments());
        return "appointment/list";
    }

}
