package com.udacity.jdnd.course3.critter.controller;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private ScheduleService scheduleService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
    	Customer customer = convertCustomerDtoToCustomerEntity(customerDTO);
    	return convertToCustomerDto(this.customerService.createCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
    	List<Customer> customers = this.customerService.getAllCustomers();
		return customers.stream().map(this::convertToCustomerDto).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
    	Pet pet = this.petService.getPetById(petId).get();
		Customer customer = pet.getCustomer();
		return convertToCustomerDto(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    	Employee employee = convertEmployeeDtoToEmployeeEntity(employeeDTO);
    	return convertEmployeeEntityToEmployeeDto(this.employeeService.createEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
    	Employee employee = this.employeeService.getEmployeeById(employeeId).get();
		return convertEmployeeEntityToEmployeeDto(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
    	Employee employee = this.employeeService.getEmployeeById(employeeId).get();
		employee.setDaysAvailable(daysAvailable);
		this.employeeService.createEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesForService = this.employeeService
        		.getEmployeeForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        return employeesForService.stream().map(e -> convertEmployeeEntityToEmployeeDto(e)).collect(Collectors.toList());
    }
    
	private CustomerDTO convertToCustomerDto(Customer customer) {
		CustomerDTO customerDto = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDto);
		if (customer.getPets() != null) {
			List<Long> petIds = customer.getPets().stream()
					.map(pet -> pet.getId())
					.collect(Collectors.toList());			
			customerDto.setPetIds(petIds);
		}
		return customerDto;
	}

	private Customer convertCustomerDtoToCustomerEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		if (customerDTO.getPetIds() != null) {
			List<Pet> pets = customerDTO.getPetIds().stream()
					.map(id -> this.petService.getPetById(id).get())
					.collect(Collectors.toList());
			customer.setPets(pets);
		}
		return customer;
	}
	
	private EmployeeDTO convertEmployeeEntityToEmployeeDto(Employee employee) {
		EmployeeDTO employeeDto = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDto);
		return employeeDto;
	}

	private Employee convertEmployeeDtoToEmployeeEntity(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		return employee;
	}
	
	private Employee convertEmployeeRequestDtoToEntity(EmployeeRequestDTO erDto) {
		Employee employee = new Employee();
		Set<DayOfWeek> days = new HashSet<>();
		days.add(erDto.getDate().getDayOfWeek());
		employee.setDaysAvailable(days);
		employee.setSkills(erDto.getSkills());
		return employee;
	}

}
