package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uns.ac.rs.ib.security.dto.HealthSerDTO;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.repository.HealthSerRepository;
import uns.ac.rs.ib.security.service.HealthSerService;

@RestController
@RequestMapping(value = "/api/healthServices")
@CrossOrigin
public class HealthSerController {

	@Autowired
	HealthSerService healthSerService; 
	
	@Autowired 
	HealthSerRepository healthSerRepository;
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<List<HealthSerDTO>> getHealthServices() {
		List<HealthSer> healthServices = healthSerService.findAll(); 
		List<HealthSerDTO> healthServicesDTO = new ArrayList<>();
		for(HealthSer h : healthServices) {
			healthServicesDTO.add(new HealthSerDTO(h));
		}
		
		return new ResponseEntity<List<HealthSerDTO>>(healthServicesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<HealthSerDTO> getHealthService(@PathVariable("id") Integer id) {
		HealthSer healthService = healthSerService.findOne(id); 
		
		if (healthService == null) {
			return new ResponseEntity<HealthSerDTO>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthService), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<HealthSerDTO> saveHealthService(@RequestBody HealthSerDTO healthSerDTO) {
		HealthSer healthSer = new HealthSer();
		healthSer.setName(healthSerDTO.getName());
		healthSer.setPrice(healthSerDTO.getPrice());
		
		healthSer = healthSerService.save(healthSer); 
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthSer), HttpStatus.CREATED);
	}
	
	@PutMapping(value="id", consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<HealthSerDTO> updateHealthService(@RequestBody HealthSerDTO healthSerDTO, @PathVariable("id") Integer id) {
		HealthSer healthSer = healthSerService.findOne(id);
		
		healthSer.setName(healthSerDTO.getName());
		healthSer.setPrice(healthSerDTO.getPrice());
		
		healthSer = healthSerService.save(healthSer); 
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthSer), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<Void> deleteHealthService(@PathVariable("id") Integer id) {
		HealthSer healthSer = healthSerService.findOne(id); 
		
		if(healthSer != null) {
			healthSerService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	
}
