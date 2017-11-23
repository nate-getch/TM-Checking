package edu.mum.model;

import edu.mum.domain.PersonRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Erdenebayar on 11/20/2017
 */
public class PersonForm implements Serializable {

    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotBlank
    private String password;
    private boolean enabled = true;
    @NotNull
    private String[] personRoles;
    private Integer addressId;

    public PersonForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(String[] personRoles) {
        this.personRoles = personRoles;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
