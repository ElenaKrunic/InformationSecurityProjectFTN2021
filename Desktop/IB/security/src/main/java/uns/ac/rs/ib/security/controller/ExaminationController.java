package uns.ac.rs.ib.security.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.service.ExaminationService;
import uns.ac.rs.ib.security.service.HealthSerService;

@RestController
@RequestMapping(value = "/api/examinations")
@CrossOrigin
public class ExaminationController {

	@Autowired
	ExaminationService examinationService; 
	
	@Autowired
	ExaminationRepository examinationRepository; 
	
	@Autowired
	HealthSerService healthService; 
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<List<ExaminationDTO>> getExaminations(){
		List<Examination> examinations = examinationService.findAll(); 
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		for(Examination e: examinations) {
			examinationsDTO.add(new ExaminationDTO(e));
		}
		
		return new ResponseEntity<List<ExaminationDTO>>(examinationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<ExaminationDTO> getExamination(@PathVariable("id") Integer id) {
		Examination examination = examinationService.findOne(id);
		
		if(examination == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(examination), HttpStatus.OK);
	}
	
	
	@PutMapping(consumes = "application/json", value="/saveExamination/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> saveExamination(@PathVariable("id") int id, Principal principal) {
		try {
			//String mess = examinationService.createExamination(id, principal.getName());
			String mess = examinationService.createExamination(id, "lelekrunic1@gmail.com");
			return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<Void> deleteExamination(@PathVariable("id") Integer id) {
		Examination examination = examinationService.findOne(id); 
		
		if(examination != null) {
			examinationService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/history-еxamination")
    @PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> historyExamination(Principal principal) {
		try {
			List<ExaminationDTORes> еxaminations = examinationService.historyExamination(principal.getName());
			return new ResponseEntity<>(еxaminations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/available-terms")
    @PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> availableTerms(Principal principal) {
		try {
			List<ExaminationDTORes> еxaminations = examinationService.availableTerms(principal.getName());
			return new ResponseEntity<>(еxaminations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}


	@PutMapping("/order-examination/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> orderExamination(@PathVariable("id") int id, Principal principal) {
		try {
			String message = examinationService.createExamination(id, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	

	@PostMapping("/order-appointment")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<?> orderAppointment(@RequestBody ExaminationDTOReq examinationDTOReq, Principal principal){
		try {
			String message = examinationService.createAppointment(examinationDTOReq, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/doctors")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<?> doctors(Principal principal) {
		try {
			List<SimpleSelectDTORes> doctors = examinationService.doctors(principal.getName());
			return new ResponseEntity<>(doctors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/nurses")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<?> nurses(Principal principal) {
		try {
			List<SimpleSelectDTORes> nurses = examinationService.nurses("lelekrunic1@gmail.com");
			return new ResponseEntity<>(nurses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/services")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<?> services(Principal principal) {
		try {
			List<SimpleSelectDTORes> sers = examinationService.services(principal.getName());
			return new ResponseEntity<>(sers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/working-calendar")
    @PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<?> workingCalendar(Principal principal) {
		try {
			List<ExaminationDTORes> examinations = examinationService.workCalendar(principal.getName());
			return new ResponseEntity<>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/working-calendar-for-nurse")
    @PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<?> workingCalendarForNurse(Principal principal) {
		try {
			List<ExaminationDTO> examinations = examinationService.nursesWorkCalendar("lelekrunic1@gmail.com");
			return new ResponseEntity<>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
 }
