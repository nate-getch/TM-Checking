package edu.mum.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue
	private int id;
	@NotNull
	@Size(min = 1, max = 25)
	private String building;

	@NotNull
	@Size(min = 1, max = 5)
	private String roomNumber;

	public Location() {
		super();
	}

	public Location(String building, String roomNumber) {
		super();
		this.building = building;
		this.roomNumber = roomNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	@Override
	public String toString() {
		return "Building=" + building + ", Room Number=" + roomNumber ;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null)
//			return false;
//
//		if (this.getClass() != obj.getClass())
//			return false;
//
//		Location location = (Location) obj;
//		return location.getBuilding().equals(this.getBuilding())
//				&& location.getRoomNumber().equals(this.getRoomNumber());
//	}
//
//	@Override
//	public int hashCode() {
//		int result = 17;
//		result += this.getBuilding().hashCode() * 31;
//		result += this.getRoomNumber().hashCode() * 31;
//		return result;
//	}

}
