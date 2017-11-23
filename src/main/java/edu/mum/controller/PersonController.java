package edu.mum.controller;

import edu.mum.domain.Person;
import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import edu.mum.model.PersonChangeRoleForm;
import edu.mum.model.PersonEditForm;
import edu.mum.model.PersonForm;
import edu.mum.service.AddressService;
import edu.mum.service.PersonRoleService;
import edu.mum.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Erdenebayar on 11/20/2017
 */
@Controller
@RequestMapping("persons")
public class PersonController {

    private final PersonService personService;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonRoleService personRoleService;

    @Autowired
    public PersonController(PersonService personService, AddressService addressService, BCryptPasswordEncoder bCryptPasswordEncoder, PersonRoleService personRoleService) {
        this.personService = personService;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personRoleService = personRoleService;
    }

    @RequestMapping
    public String index(ModelMap model) {
        model.addAttribute("header_title", "Person List");
        model.addAttribute("list", personService.find());
        return "person/index";
    }

    @RequestMapping("{id}")
    public String list(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("person", personService.get(id));
        return "person/view";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(ModelMap model) {
        model.addAttribute("header_title", "Add Person");
        model.addAttribute("roles", Role.values());
        model.addAttribute("moduleForm", new PersonForm());
        return "person/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("moduleForm") PersonForm form, BindingResult result, RedirectAttributes redirect, ModelMap model) {
        model.addAttribute("header_title", "Add Person");
        model.addAttribute("roles", Role.values());
        if (result.hasErrors()) {
            return "person/create";
        }
        Person person = new Person();
        if (form.getAddressId() != null)
            person.setAddress(addressService.get(form.getAddressId()));
        person.setEmail(form.getEmail());

        if (personService.findByUsername(form.getUsername()) != null) {
            result.rejectValue("username", null, "This username is already taken");
            return "person/create";
        }

        person.setUsername(form.getUsername());
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        person.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        Set<PersonRole> roles = new HashSet<>();
        for (String role : form.getPersonRoles()) {
            PersonRole personRole = new PersonRole(person, Role.valueOf(role));
            roles.add(personRole);
        }
        person.setPersonRoles(roles);
        personService.save(person);

        redirect.addFlashAttribute("successMessage", "Successfully saved");

        return "redirect:/persons";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("header_title", "Edit Person");
        model.addAttribute("moduleForm", new PersonEditForm(personService.get(id)));
        return "person/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("moduleForm") PersonEditForm form, BindingResult result, RedirectAttributes redirect, ModelMap model) {
        model.addAttribute("header_title", "Edit Person");
        if (result.hasErrors()) {
            return "person/edit";
        }
        Person person = personService.get(form.getId());
        if (form.getAddressId() != null)
            person.setAddress(addressService.get(form.getAddressId()));
        person.setEmail(form.getEmail());

        if (personService.findByUsernameAndId(form.getUsername(), person.getId()) != null) {
            result.rejectValue("username", null, "This username is already taken");
            return "person/edit";
        }

        person.setUsername(form.getUsername());
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        personService.save(person);

        redirect.addFlashAttribute("successMessage", "Successfully edited");

        return "redirect:/persons";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, RedirectAttributes redirect, ModelMap model) {
        if (personService.get(id) == null) {
            redirect.addAttribute("errorMessage", "Please select valid person to delete");
            return "redirect:/persons";
        }
        personService.delete(id);
        redirect.addFlashAttribute("successMessage", "Successfully deleted");

        return "redirect:/persons";
    }

    @RequestMapping(value = "changeRole/{id}", method = RequestMethod.GET)
    public String changeRole(@PathVariable Integer id, RedirectAttributes redirect, ModelMap model) {
        model.addAttribute("header_title", "Change Role");
        Person person = personService.get(id);
        if (person == null) {
            redirect.addFlashAttribute("errorMessage", "Please select valid person to change role");
            return "redirect:/persons";
        }

        model.addAttribute("roles", Role.values());
        model.addAttribute("moduleForm", new PersonChangeRoleForm(person));
        return "person/changeRole";
    }

    @RequestMapping(value = "changeRole", method = RequestMethod.POST)
    public String changeRole(@Valid @ModelAttribute("moduleForm") PersonChangeRoleForm form, BindingResult result, RedirectAttributes redirect, ModelMap model) {
        model.addAttribute("header_title", "Change Role");
        model.addAttribute("roles", Role.values());
        if (result.hasErrors()) {
            return "person/changeRole";
        }
        Person person = personService.get(form.getId());
        Set<PersonRole> roles = new HashSet<>();
        for (String role : form.getRoles()) {
            PersonRole personRole = personRoleService.findByPersonAndRole(person.getId(), Role.valueOf(role));
            if (personRole == null)
                personRole = new PersonRole(person, Role.valueOf(role));
            roles.add(personRole);
        }
        person.setPersonRoles(roles);
        personService.save(person);

        redirect.addFlashAttribute("successMessage", "Successfully saved user #" + person.getId() + "'s role");

        return "redirect:/persons";
    }
}
