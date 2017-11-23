package edu.mum.controller;

import edu.mum.domain.Person;
import edu.mum.model.ChangePasswordForm;
import edu.mum.model.PersonEditForm;
import edu.mum.service.AddressService;
import edu.mum.service.EmailService;
import edu.mum.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Erdenebayar on 11/21/2017
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    private final PersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AddressService addressService;

    @Autowired
    public ProfileController(PersonService personService, BCryptPasswordEncoder passwordEncoder, EmailService emailService, AddressService addressService) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.addressService = addressService;
    }

    private Person currentUser() {
        return personService.getCurrentAuthenticatedUser();
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePassword(ModelMap model) {
        Person person = currentUser();
        if (person == null) {
            return "redirect:/login";
        }
        model.addAttribute("moduleForm", new ChangePasswordForm(person.getId()));
        return "profile/changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("moduleForm") ChangePasswordForm form, BindingResult result, RedirectAttributes redirect, ModelMap model) {
        if (result.hasErrors()) {
            return "profile/changePassword";
        }

        if (form.isPasswordConfirmed()) {
            Person person = currentUser();
            person.setPassword(passwordEncoder.encode(form.getPasswordConfirm()));
            personService.save(person);
            redirect.addFlashAttribute("successMessage", "Successfully edited");
        }

        return "redirect:/appointment";
    }

    @RequestMapping(value = "testEmail")
    @ResponseBody public String sendEmail(){

        emailService.send("erdenebayar@hi.com", "tomjaal@gmail.com", "how are you doing?", "message", null);
        return "success";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(ModelMap model) {
        model.addAttribute("header_title", "Edit profile");
        model.addAttribute("moduleForm", new PersonEditForm(personService.getCurrentAuthenticatedUser()));
        return "profile/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("moduleForm") PersonEditForm form, BindingResult result, RedirectAttributes redirect, ModelMap model) {
        model.addAttribute("header_title", "Edit profile");
        if (result.hasErrors()) {
            return "profile/edit";
        }
        Person person = personService.getCurrentAuthenticatedUser();
        if (form.getAddressId() != null)
            person.setAddress(addressService.get(form.getAddressId()));
        person.setEmail(form.getEmail());

        if (personService.findByUsernameAndId(form.getUsername(), person.getId()) != null) {
            result.rejectValue("username", null, "This username is already taken");
            return "profile/edit";
        }

        person.setUsername(form.getUsername());
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        personService.save(person);

        redirect.addFlashAttribute("successMessage", "Successfully edited");

        return "redirect:/appointment";
    }

}
