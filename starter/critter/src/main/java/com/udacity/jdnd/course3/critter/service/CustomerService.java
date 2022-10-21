package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer createCustomer(Customer customer) {
		return this.customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();
	}

	public Optional<Customer> getCustomerById(Long customerId) {
		return this.customerRepository.findById(customerId);
	}

}
