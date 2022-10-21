package com.udacity.jdnd.course3.critter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToScheduleEntity(scheduleDTO);
        Schedule scheduleCreated = this.scheduleService.createSchedule(schedule);
        return convertScheduleEntityToScheduleDTO(scheduleCreated);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
    	List<Schedule> schedules = this.scheduleService.getAllSchedules();
		return schedules.stream().map(this::convertScheduleEntityToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    	Pet pet = this.petService.getPetById(petId).get();
        List<Schedule> schedules = this.scheduleService.getScheduleForPet(pet);
        return schedules.stream().map(p  -> convertScheduleEntityToScheduleDTO(p)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = this.employeeService.getEmployeeById(employeeId).get();
        List<Schedule> schedules = this.scheduleService.getScheduleForEmployee(employee);
        return schedules.stream().map(s  -> convertScheduleEntityToScheduleDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    	List<Pet> customersPets = this.petService.getPetsByCustomerId(customerId);
    	List<List<ScheduleDTO>> schedules = customersPets.stream()
    			.map(p -> this.getScheduleForPet(p.getId()))
    			.collect(Collectors.toList());
    	List<ScheduleDTO> customerSchedules = new ArrayList<>();
    	schedules.stream()
    			.forEach(s -> {
    				for(ScheduleDTO scheduleDto : s) {
    					customerSchedules.add(scheduleDto);
    				}
    			});
    	return customerSchedules;
    }
    
    private Schedule convertScheduleDtoToScheduleEntity(ScheduleDTO scheduleDto) {
    	Schedule schedule = new Schedule();
    	BeanUtils.copyProperties(scheduleDto, schedule);
    	
    	if(scheduleDto.getEmployeeIds() != null) {
    		List<Employee> employees = scheduleDto.getEmployeeIds().stream()
    				.map(id -> this.employeeService.getEmployeeById(id).get())
    				.collect(Collectors.toList());
    		schedule.setEmployees(employees);
    	}
    	
    	if(scheduleDto.getPetIds() != null) {
    		List<Pet> pets = scheduleDto.getPetIds().stream()
    				.map(id -> this.petService.getPetById(id).get())
    				.collect(Collectors.toList());
    		schedule.setPets(pets);
    	}
    	
    	return schedule;
    }
    
    private ScheduleDTO convertScheduleEntityToScheduleDTO(Schedule schedule) {
    	ScheduleDTO scheduleDto = new ScheduleDTO();
    	BeanUtils.copyProperties(schedule, scheduleDto);
    	
    	if(schedule.getEmployees() != null) {
    		List<Long> empIds = schedule.getEmployees().stream()
    				.map(e -> e.getId())
    				.collect(Collectors.toList());
    		scheduleDto.setEmployeeIds(empIds);
    	}
    	
    	if(schedule.getPets() != null) {
    		List<Long> petIDs = schedule.getPets().stream()
    				.map(p -> p.getId())
    				.collect(Collectors.toList());
    		scheduleDto.setPetIds(petIDs);
    	}
    	
    	return scheduleDto;
    }
   
}
