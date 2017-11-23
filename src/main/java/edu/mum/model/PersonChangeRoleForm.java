package edu.mum.model;

import edu.mum.domain.Person;
import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Erdenebayar on 11/21/2017
 */
public class PersonChangeRoleForm implements Serializable {

    @NotNull
    private Integer id;
    private String username;
    @NotEmpty
    private Set<String> roles = new HashSet<>();

    public PersonChangeRoleForm() {
    }

    public PersonChangeRoleForm(Person person) {
        this.id = person.getId();
        this.username = person.getUsername();
        for (PersonRole personRole : person.getPersonRoles()){
            roles.add(personRole.getRole().toString());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
