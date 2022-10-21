package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

@Service
@Transactional
public class PetService {
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Pet> getAllPets(){
		return this.petRepository.findAll();
	}

	public Optional<Pet> getPetById(Long petId) {
		return this.petRepository.findById(petId);
	}

	public Pet createPet(Pet pet) {
		Pet newPet = petRepository.save(pet);
        Customer customer = newPet.getCustomer();
        List<Pet> pets = customer.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        if (!pets.contains(newPet)) {
            pets.add(newPet);
            customer.setPets(pets);
        }
        return newPet;
	}

	public List<Pet> getPetsByCustomerId(long ownerId) {
		Customer customer = this.customerRepository.findById(ownerId).get();
		return this.petRepository.findByCustomer(customer);
	}

}
