package edu.mum.domain;

import org.hibernate.annotations.Type;
import org.hibernate.type.BasicType;

import javax.persistence.*;

@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "session_id")
	private Session session;

    @Column(columnDefinition = "TINYINT(1)")
	private boolean sentEmail = false;

	public Appointment() {
		super();
	}

	public Appointment(Person person, Session session) {
		super();
		this.person = person;
		this.session = session;
		this.sentEmail = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

    public boolean isSentEmail() {
        return sentEmail;
    }

    public void setSentEmail(boolean sentEmail) {
        this.sentEmail = sentEmail;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Appointment that = (Appointment) o;

		return id == that.id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
