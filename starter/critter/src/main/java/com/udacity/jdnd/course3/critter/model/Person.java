package com.udacity.jdnd.course3.critter.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Nationalized;

/**
 * I don't see the reason to have separate table for Person Entity.
 * Attributes from Person will be in the child tables
 */
@MappedSuperclass
public class Person {

	@Nationalized
	@Column
	private String name;
	
	@Nationalized
	@Column(length = 4000)
	private String notes;

	@Column
	private String phoneNumber;
	
	public Person() {}
	
	public Person(String name, String notes, String phoneNumber) {
		super();
		this.name = name;
		this.notes = notes;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", notes=" + notes + ", phoneNumber=" + phoneNumber + "]";
	}


}
