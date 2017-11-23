package edu.mum.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "session")
public class Session {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date date;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	@Temporal(TemporalType.TIME)
	private Date startTime;

	@Min(10)
	private int duration;
	
	@Min(1)
	private int numberofsits;
	
	private int availablesits;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(mappedBy = "session", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Appointment> appointments;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "counselor_id")
	private Person person;

	public Session() {
		super();
	}

	public Session(Date date, Date startTime, int duration, int numberofsits, int availablesits, Location location, Person person) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.duration = duration;
		this.location = location;
		this.numberofsits = numberofsits;
		this.availablesits = availablesits;
		this.person = person;
		person.addSession(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getNumberofsits() {
		return numberofsits;
	}

	public void setNumberofsits(int numberofsits) {
		this.numberofsits = numberofsits;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAvailablesits() {
		return availablesits;
	}

	public void setAvailablesits(int availablesits) {
		this.availablesits = availablesits;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null)
//			return false;
//
//		if (this.getClass() != obj.getClass())
//			return false;
//
//		Session session = (Session) obj;
//		return session.getId() == this.getId() 
//				&& session.getDate().compareTo(this.getDate()) == 0
//				&& session.getStartTime().compareTo(this.getStartTime()) == 0
//				&& session.getDuration() == this.getDuration() 
//				&& session.getLocation().equals(this.getLocation())
//				&& session.getNumberofsits() == session.getNumberofsits() ;
////				&& session.getPerson().equals(this.getPerson());
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(this.getId(), this.getDate(), this.getDuration(),
//				// this.getLocation(), this.getPerson(), this.getStartTime(),
//				// this.getNumberofsits());
//				this.getLocation(), this.getStartTime(), this.getNumberofsits());
//	}

}
