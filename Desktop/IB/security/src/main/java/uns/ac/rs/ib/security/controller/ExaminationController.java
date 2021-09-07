package uns.ac.rs.ib.security.controller;

import java.security.Principal;
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

import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.service.ExaminationService;

@RestController
@RequestMapping(value = "/api/examinations")
public class ExaminationController {

	@Autowired
	ExaminationService examinationService; 
	
	@Autowired
	ExaminationRepository examinationRepository; 
	
	@GetMapping(value="/all")
	public ResponseEntity<List<ExaminationDTO>> getExaminations(){
		List<Examination> examinations = examinationService.findAll(); 
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		for(Examination e: examinations) {
			examinationsDTO.add(new ExaminationDTO(e));
		}
		
		return new ResponseEntity<List<ExaminationDTO>>(examinationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ExaminationDTO> getExamination(@PathVariable("id") Integer id) {
		Examination examination = examinationService.findOne(id);
		
		if(examination == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ExaminationDTO>(new ExaminationDTO(examination), HttpStatus.OK);
	}
	
	//NAPRAVITI EXAMINATION RES/REQ ZBOG DATUMA
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ExaminationDTO> saveExamination(@RequestBody ExaminationDTO examinationDTO) {
		Examination examination = new Examination(); 
		examination.setDataAboutExamination(examinationDTO.getDataAboutExamination());
		//examination.setDate(examinationDTO.getDate());
		examination.setDiscount(examinationDTO.getDiscount());
		examination.setDuration(examinationDTO.getDuration());
		
		examination = examinationService.save(examination); 
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.CREATED);
		
	}
	
	@PutMapping(consumes = "application/json", value="/{id}")
	public ResponseEntity<ExaminationDTO> updateExamination(@RequestBody ExaminationDTO examinationDTO, @PathVariable("id") Integer id) {
		
		Examination examination = examinationService.findOne(id); 
		
		if(examination == null) {
			return new ResponseEntity<ExaminationDTO>(HttpStatus.NOT_FOUND);
		}
		
		examination.setDataAboutExamination(examinationDTO.getDataAboutExamination());
		//examination.setDiscount(examinationDTO.getDiscount());
		examination.setDuration(examinationDTO.getDuration());
		
		examination = examinationService.save(examination); 
		return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.CREATED);
		
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteExamination(@PathVariable("id") Integer id) {
		Examination examination = examinationService.findOne(id); 
		
		if(examination != null) {
			examinationService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/history-еxamination")
	public ResponseEntity<?> historyExamination(Principal principal) {
		try {
			List<ExaminationDTORes> еxaminations = examinationService.historyExamination(principal.getName());
			return new ResponseEntity<>(еxaminations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/available-terms")
	public ResponseEntity<?> availableTerms(Principal principal) {
		try {
			List<ExaminationDTORes> еxaminations = examinationService.availableTerms(principal.getName());
			return new ResponseEntity<>(еxaminations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/order-examination/{id}")
	public ResponseEntity<?> orderExamination(@PathVariable("id") int id, Principal principal) {
		try {
			String message = examinationService.createExamination(id, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/order-appointment")
	public ResponseEntity<?> orderAppointment(@RequestBody ExaminationDTOReq examinationDTOReq, Principal principal){
		try {
			String message = examinationService.createAppointment(examinationDTOReq, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/doctors")
	public ResponseEntity<?> doctors(Principal principal) {
		try {
			List<SimpleSelectDTORes> doctors = examinationService.doctors(principal.getName());
			return new ResponseEntity<>(doctors, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/nurses")
	public ResponseEntity<?> nurses(Principal principal) {
		try {
			List<SimpleSelectDTORes> nurses = examinationService.nurses("lelekrunic1@gmail.com");
			return new ResponseEntity<>(nurses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/services")
	public ResponseEntity<?> services(Principal principal) {
		try {
			List<SimpleSelectDTORes> sers = examinationService.services(principal.getName());
			return new ResponseEntity<>(sers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/working-calendar")
	public ResponseEntity<?> workingCalendar(Principal principal) {
		try {
			List<ExaminationDTORes> examinations = examinationService.workCalendar(principal.getName());
			return new ResponseEntity<>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/working-calendar-for-nurse")
	public ResponseEntity<?> workingCalendarForNurse(Principal principal) {
		try {
			List<ExaminationDTO> examinations = examinationService.nursesWorkCalendar("lelekrunic1@gmail.com");
			return new ResponseEntity<>(examinations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
 }
