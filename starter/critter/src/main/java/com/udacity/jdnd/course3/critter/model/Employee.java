package com.udacity.jdnd.course3.critter.model;

import java.time.DayOfWeek;
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
@Table(name = "EMPLOYEE")
public class Employee extends Person{

	@Id
	@GeneratedValue
	private Long id;
	
	@ElementCollection
    @Enumerated(EnumType.STRING)
	private Set<EmployeeSkill> skills;
	
	@ElementCollection
    @Enumerated(EnumType.STRING)
	private Set<DayOfWeek> daysAvailable;
	
	@ManyToMany
	private List<Schedule> schedules;

	public Employee() {}

	public Employee(Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, List<Schedule> schedules) {
		super();
		this.skills = skills;
		this.daysAvailable = daysAvailable;
		this.schedules = schedules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<EmployeeSkill> getSkills() {
		return skills;
	}

	public void setSkills(Set<EmployeeSkill> skills) {
		this.skills = skills;
	}

	public Set<DayOfWeek> getDaysAvailable() {
		return daysAvailable;
	}

	public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
		this.daysAvailable = daysAvailable;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", skills=" + skills + ", daysAvailable=" + daysAvailable + ", schedules="
				+ schedules + "]";
	}

}
