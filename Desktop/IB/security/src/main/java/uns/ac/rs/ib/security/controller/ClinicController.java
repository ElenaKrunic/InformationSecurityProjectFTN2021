package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.ib.security.dto.ClinicDTORes;
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
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN') || hasAuthority('PATIENT')")
	public ResponseEntity<List<ClinicDTORes>> getClinics() {
		List<Clinic> clinics = clinicService.findAll(); 
		List<ClinicDTORes> clinicsDTO = new ArrayList<>();
		for(Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTORes(c));
		}
		return new ResponseEntity<List<ClinicDTORes>>(clinicsDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN') || hasAuthority('PATIENT')")
	public ResponseEntity<ClinicDTORes> getClinic(@PathVariable("id") Integer id) {
		Clinic clinic = clinicService.findOne(id); 
		if(clinic == null) {
			return new ResponseEntity<ClinicDTORes>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ClinicDTORes>(new ClinicDTORes(clinic), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<ClinicDTORes> saveClinic(@RequestBody ClinicDTORes clinicDTO) {
		Clinic clinic = new Clinic(); 
		clinic.setAddress(clinicDTO.getAddress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setName(clinicDTO.getName());
		
		clinic = clinicService.save(clinic); 
		return new ResponseEntity<> (new ClinicDTORes(clinic), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<ClinicDTORes> updateClinic(@RequestBody ClinicDTORes clinicDTO, @PathVariable("id") Integer id) {
		
		Clinic clinic = clinicService.findOne(id);
		
		if (clinic == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		clinic.setAddress(clinicDTO.getAddress());
		clinic.setDescription(clinicDTO.getDescription());
		clinic.setName(clinicDTO.getName());
		
		clinic = clinicService.save(clinic); 
		return new ResponseEntity<> (new ClinicDTORes(clinic), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<Void> deleteClinic(@PathVariable("id") Integer id) {
		Clinic clinic = clinicService.findOne(id); 
		

		if (clinic != null) {
			clinicService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
}
