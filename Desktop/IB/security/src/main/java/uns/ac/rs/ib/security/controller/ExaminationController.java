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
	
 }
