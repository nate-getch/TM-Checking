package edu.mum.service.impl;

import edu.mum.domain.Person;
import edu.mum.domain.Role;
import edu.mum.repository.PersonRepo;
import edu.mum.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 985927 on 11/20/2017
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService{

    private final PersonRepo personRepo;

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public void save(Person person) {
        personRepo.save(person);
    }

    @Override
    public void update(Person person) {
        personRepo.save(person);
    }

    @Override
    public List<Person> find() {
        return (List<Person>) personRepo.findAll();
    }

    @Override
    public Person get(Integer id) {
        return personRepo.findOne(id);
    }

    @Override
    public Person findByUsername(String username) {
        return personRepo.findByUsername(username);
    }

    @Override
    public Person findByUsernameAndId(String username, Integer id) {
        return personRepo.findByUsernameAndIdIsNot(username, id);
    }

    @Override
    public List<Person> findByPersonRoles(Role role) {
        return (List<Person>) personRepo.findByPersonRoles(role);
    }

    @Override
    public Person getCurrentAuthenticatedUser() {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println("LoggedIn USER IS ...... " + username);
        return personRepo.findByUsername(username);
    }

    @Override
    public void delete(Integer id) {
        personRepo.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepo.findByUsername(username);
    }
}
