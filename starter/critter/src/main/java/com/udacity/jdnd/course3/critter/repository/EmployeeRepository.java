package com.udacity.jdnd.course3.critter.repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findByDaysAvailableContains(DayOfWeek daysAvailable);

}
