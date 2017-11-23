package edu.mum.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.mum.domain.Person;
import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import edu.mum.domain.Session;
import edu.mum.service.PersonService;
import edu.mum.service.SessionService;

/**
 * @author 985727 on 11/20/2017
 */
@Controller
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private PersonService personService;
	
	Person currentPerson;
	boolean isAdmin;

	@PostConstruct
	public void populateCustomers() {
		
	}

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String getSessions(Model model) {
		isAdmin = false;
		model.addAttribute("header_title", "My Session List");
		currentPerson = personService.getCurrentAuthenticatedUser();
		for(PersonRole role : currentPerson.getPersonRoles()) {
			if(role.getRole().equals(Role.ROLE_ADMIN))
				isAdmin = true;
		}
		List<Session> sessions = (List<Session>) sessionService.findAll().stream()
				.filter(sess -> sess.getDate().after(new Date()))
				.sorted((x, y)-> x.getDate().compareTo(y.getDate())).collect(Collectors.toList());
		model.addAttribute("sessions", sessions);
		model.addAttribute("currentPerson", currentPerson);
		model.addAttribute("searchValue", "");
		return "session/sessions";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewSessionForm(@ModelAttribute("session") Session newSession, Model model) {
		model.addAttribute("header_title", "My Session List");
		model.addAttribute("isAdmin", isAdmin);
		if(isAdmin) {
			model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
		}
		else {
			newSession.setPerson(currentPerson);
			model.addAttribute("session", newSession);
		}
		return "session/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNewSession(@Valid @ModelAttribute("session") Session addSession, BindingResult result,
			RedirectAttributes redirect, Model model) {
		model.addAttribute("header_title", "My Session List");
		if (result.hasErrors()) {
			model.addAttribute("isAdmin", isAdmin);
			if(isAdmin) {
				model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
			}
			else {
				addSession.setPerson(currentPerson);
				model.addAttribute("session", addSession);
			}
			return "session/add";
		}
		
		if(addSession.getPerson().getId() == 0 && isAdmin == true) {
			model.addAttribute("isAdmin", isAdmin);
			if(isAdmin) {
				model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
			}
			else {
				addSession.setPerson(currentPerson);
				model.addAttribute("session", addSession);
			}
			result.rejectValue("person.id", null, "Please choose counselor of session!");
			return "session/add";
		}
		
		if(addSession.getDate().before(new Date())) {
			model.addAttribute("isAdmin", isAdmin);
			if(isAdmin) {
				model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
			}
			else {
				addSession.setPerson(currentPerson);
				model.addAttribute("session", addSession);
			}
			result.rejectValue("date", null, "Please change date of session!");
			return "session/add";
		}
		
		if(addSession.getId() != 0) {
//			Session oldSession = sessionService.get(addSession.getId());
//			addSession.setAppointments(oldSession.getAppointments());
			if(addSession.getAvailablesits() > addSession.getNumberofsits()) {
				model.addAttribute("isAdmin", isAdmin);
				if(isAdmin) {
					model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
				}
				else {
					addSession.setPerson(currentPerson);
					model.addAttribute("session", addSession);
				}
				result.rejectValue("numberofsits", null, "Please increase number of sits of session! It should be greater than " + addSession.getAvailablesits());
				return "session/add";
			}
		}
		
		Person person = personService.get(addSession.getPerson().getId());
		person.getSession().clear();
		addSession.setPerson(person);
		sessionService.save(addSession);

		redirect.addFlashAttribute("successMessage", "Successfully saved");
		return "redirect:/session";
	}

	@RequestMapping(value = "/remove/{sessionId}", method = RequestMethod.GET)
	public String removeSession(@PathVariable("sessionId") int sessionId, RedirectAttributes redirect) {
		Session removing = sessionService.get(sessionId);
		if(removing.getAppointments() != null && !removing.getAppointments().isEmpty()) {
			redirect.addFlashAttribute("errorMessage", "You can not delete that session because it has customer!!!");
			return "redirect:/session";
		}
		sessionService.removeById(sessionId);
		return "redirect:/session";
	}

	@RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
	public String getEditSessionForm(@PathVariable int sessionId, Model model) {
		model.addAttribute("header_title", "My Session List");
		model.addAttribute("isAdmin", isAdmin);
		if(isAdmin) {
			model.addAttribute("counselors", personService.findByPersonRoles(Role.ROLE_COUNCELOR));
		}
		Session editSession = sessionService.get(sessionId);
		model.addAttribute("session", editSession);
		
		return "session/edit";
	}
	
	@RequestMapping(value = { "/search/{searchValue}" }, method = RequestMethod.GET)
	public String filterSessions(@PathVariable String searchValue, Model model) {
		isAdmin = false;
		model.addAttribute("header_title", "My Session List");
		currentPerson = personService.getCurrentAuthenticatedUser();
		for(PersonRole role : currentPerson.getPersonRoles()) {
			if(role.getRole().equals(Role.ROLE_ADMIN))
				isAdmin = true;
		}
		List<Session> sessions = (List<Session>) sessionService.findAll().stream()
				.filter(sess -> sess.getDate().after(new Date()))
				.sorted((x, y)-> x.getDate().compareTo(y.getDate())).collect(Collectors.toList());
		if(!searchValue.isEmpty() && searchValue != null) {
			sessions = sessions.stream().filter(y->y.getLocation().getBuilding().toLowerCase().contains(searchValue.toLowerCase())
						|| y.getLocation().getRoomNumber().toLowerCase().contains(searchValue.toLowerCase())
						|| y.getPerson().getFirstName().toLowerCase().contains(searchValue.toLowerCase())).collect(Collectors.toList());
		}
		model.addAttribute("sessions", sessions);
		model.addAttribute("currentPerson", currentPerson);
		model.addAttribute("searchValue", "");
		return "session/sessions";
	}
	
}
