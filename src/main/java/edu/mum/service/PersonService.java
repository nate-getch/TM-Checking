package edu.mum.service;

import edu.mum.domain.Person;
import edu.mum.domain.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 985927 on 11/20/2017
 */
@Transactional(readOnly = true)
public interface PersonService extends UserDetailsService {

    @Transactional
    @PreAuthorize("#person.id == principal.id or hasRole('ROLE_ADMIN')")
    void save(Person person);

    @Transactional
    @PreAuthorize("#person.id == principal.id or hasRole('ROLE_ADMIN')")
    void update(Person person);

    @PreAuthorize("#id == principal.id or hasRole('ROLE_ADMIN')")
    void delete(Integer id);

    List<Person> find();

    Person get(Integer id);

    Person findByUsername(String username);

    Person findByUsernameAndId(String username, Integer id);

    List<Person> findByPersonRoles(Role role);

    Person getCurrentAuthenticatedUser();
}
