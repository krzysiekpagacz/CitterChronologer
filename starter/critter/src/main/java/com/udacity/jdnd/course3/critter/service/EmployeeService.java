package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee createEmployee(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	public Optional<Employee> getEmployeeById(long employeeId) {
		return this.employeeRepository.findById(employeeId);
	}
	
	public List<Employee> getEmployeeForService(Set<EmployeeSkill> skills, DayOfWeek daysAvailable){
		List<Employee> employees = this.employeeRepository.findByDaysAvailableContains(daysAvailable);
		return employees.stream().filter(e -> e.getSkills().containsAll(skills)).collect(Collectors.toList());
	}

}
