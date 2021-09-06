package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.ib.security.dto.ClinicDTO;
import uns.ac.rs.ib.security.model.Clinic;
import uns.ac.rs.ib.security.repository.ClinicRepository;
import uns.ac.rs.ib.security.service.ClinicService;

@RestController
@RequestMapping(value = "/api/clinics")
public class ClinicController {
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	ClinicRepository clinicRepository; 

	@GetMapping(value="/all")
	public ResponseEntity<List<ClinicDTO>> getClinics() {
		List<Clinic> clinics = clinicService.findAll(); 
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for(Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}
		return new ResponseEntity<List<ClinicDTO>>(clinicsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ClinicDTO> getClinic(@PathVariable("id") Integer id) {
		Clinic clinic = clinicService.findOne(id); 
		if(clinic == null) {
			return new ResponseEntity<ClinicDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ClinicDTO>(new ClinicDTO(clinic), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {
		Clinic clinic = new Clinic(); 
		clinic.setAddress(clinicDTO.getAddress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setName(clinicDTO.getName());
		
		clinic = clinicService.save(clinic); 
		return new ResponseEntity<> (new ClinicDTO(clinic), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO, @PathVariable("id") Integer id) {
		
		Clinic clinic = clinicService.findOne(id);
		
		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		clinic.setAddress(clinicDTO.getAddress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setName(clinicDTO.getName());
		
		clinic = clinicService.save(clinic); 
		return new ResponseEntity<> (new ClinicDTO(clinic), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteClinic(@PathVariable("id") Integer id) {
		Clinic clinic = clinicService.findOne(id); 
		

		if (clinic != null) {
			clinicService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
}
