package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "PET")
public class Pet {
	
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private PetType type;
	
	@Column
	@Nationalized
	private String name;
	
	@Column
	private LocalDate birthDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(length = 4000)
	@Nationalized
	private String notes;

	public Pet() {}

	public Pet(PetType type, String name, LocalDate birthDate, Customer customer, String notes) {
		super();
		this.type = type;
		this.name = name;
		this.birthDate = birthDate;
		this.customer = customer;
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PetType getType() {
		return type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", type=" + type + ", name=" + name + ", birthDate=" + birthDate + ", customer="
				+ customer + ", notes=" + notes + "]";
	}

}
