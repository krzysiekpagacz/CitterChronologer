package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	public Schedule createSchedule(Schedule schedule) {
		return this.scheduleRepository.save(schedule);
	}

	public List<Schedule> getAllSchedules() {
		return this.scheduleRepository.findAll();
	}
	
	public List<Schedule> getScheduleForPet(Pet pet) {
		return this.scheduleRepository.findByPetsId(pet.getId());
	}

	public List<Schedule> getScheduleForEmployee(Employee employee) {
		return this.scheduleRepository.findByEmployeesId(employee.getId());
	}

}
