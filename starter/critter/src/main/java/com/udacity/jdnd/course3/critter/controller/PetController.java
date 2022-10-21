package com.udacity.jdnd.course3.critter.controller;

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

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDto) {
        Pet pet = convertPetDtoToPetEntity(petDto);
    	Pet petCreated = this.petService.createPet(pet);
    	return convertPetEntityToPetDto(petCreated); 
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
    	Pet pet = this.petService.getPetById(petId).get();
        return convertPetEntityToPetDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = this.petService.getAllPets();
        return pets.stream()
        		.map(this::convertPetEntityToPetDto)
        		.collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    	List<Pet> pets = this.petService.getPetsByCustomerId(ownerId);
        return pets.stream().map(this::convertPetEntityToPetDto).collect(Collectors.toList());
    }
    
    private Pet convertPetDtoToPetEntity(PetDTO petDto) {
    	Pet pet = new Pet();
    	BeanUtils.copyProperties(petDto, pet);
    	Long customerId = petDto.getOwnerId();
    	pet.setCustomer(customerService.getCustomerById(customerId).get());
    	return pet;
    }
    
    private PetDTO convertPetEntityToPetDto(Pet pet) {
    	PetDTO petDto = new PetDTO();
    	BeanUtils.copyProperties(pet, petDto);
    	petDto.setOwnerId(pet.getCustomer().getId());
    	return petDto;
    }
}
