package com.udacity.jdnd.course3.critter.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {
	
    @Id
    @GeneratedValue
    private Long id;
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    @ManyToMany
    private List<Employee> employees;

	@ManyToMany
    private List<Pet> pets;
    
    private LocalDate date;

    public Schedule() {}
    
    public Schedule(Set<EmployeeSkill> activities, List<Employee> employees, List<Pet> pets, LocalDate date) {
		super();
		this.activities = activities;
		this.employees = employees;
		this.pets = pets;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<EmployeeSkill> getActivities() {
		return activities;
	}

	public void setActivities(Set<EmployeeSkill> activities) {
		this.activities = activities;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", activities=" + activities + ", employees=" + employees + ", pets=" + pets
				+ ", date=" + date + "]";
	}

}
