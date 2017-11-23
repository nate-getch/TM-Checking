package edu.mum.model;

import edu.mum.domain.Person;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 11/20/2017
 */
public class PersonEditForm implements Serializable {

    @NotNull
    private Integer id;
    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    private Integer addressId;

    public PersonEditForm() {
    }

    public PersonEditForm(Person person) {
        this.id = person.getId();
        this.username = person.getUsername();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        if (person.getAddress() != null) {
            this.addressId = person.getAddress().getId();
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
