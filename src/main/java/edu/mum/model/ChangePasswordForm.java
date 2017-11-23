package edu.mum.model;

import edu.mum.domain.Person;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Erdenebayar on 11/20/2017
 */
public class ChangePasswordForm implements Serializable {

    @NotNull
    private Integer id;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;

    public ChangePasswordForm() {
    }

    public ChangePasswordForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @AssertTrue(message = "Passwords not matching each other")
    public boolean isPasswordConfirmed() {
        return Objects.equals(password, passwordConfirm);
    }
}
