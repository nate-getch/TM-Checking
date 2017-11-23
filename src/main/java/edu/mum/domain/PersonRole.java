package edu.mum.domain;

import javax.persistence.*;

/**
 * Created by 985927 on 11/20/2017
 */
@Entity
@Table(name = "person_role",uniqueConstraints = @UniqueConstraint(columnNames = {"role", "person_id"}))
public class PersonRole {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER  )
    @JoinColumn(nullable = false)
    private Person person;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public PersonRole() {
    }

    public PersonRole(Person person, Role role) {
        this.person = person;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonRole that = (PersonRole) o;

        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
