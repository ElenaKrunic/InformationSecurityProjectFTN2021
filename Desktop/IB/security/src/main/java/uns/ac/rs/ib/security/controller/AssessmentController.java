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

import uns.ac.rs.ib.security.dto.AssessmentDTO;
import uns.ac.rs.ib.security.model.Assessment;
import uns.ac.rs.ib.security.repository.AssessmentRepository;
import uns.ac.rs.ib.security.service.AssessmentService;

@RestController
@RequestMapping(value = "/api/assessemnts")
public class AssessmentController {

	@Autowired
	AssessmentService assessmentService; 
	
	@Autowired
	AssessmentRepository assessmentRepository; 
	
	@GetMapping(value="/all")
	public ResponseEntity<List<AssessmentDTO>> getAssessments() {
		List<Assessment> assessments = assessmentService.findAll(); 
		List<AssessmentDTO> assessmentsDTO = new ArrayList<>();
		for(Assessment a : assessments) {
			assessmentsDTO.add(new AssessmentDTO(a));
		}
		return new ResponseEntity<List<AssessmentDTO>>(assessmentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AssessmentDTO> getAssessment(@PathVariable Long id) {
		Assessment a = assessmentService.findOne(id); 
		if(a == null) {
			return new ResponseEntity<AssessmentDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AssessmentDTO>(new AssessmentDTO(a), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<AssessmentDTO> saveAssessment(@RequestBody AssessmentDTO adto) {
		Assessment a = new Assessment(); 
		a.setAssessmentDoctor(adto.getAssesmentDoctor());
		a.setAssessmentClinic(adto.getAssesmentClinic());
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTO(a), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<AssessmentDTO> updateClinic(@RequestBody AssessmentDTO assessmentDTO, @PathVariable("id") Long id) {
		
		Assessment a = new Assessment(); 
		a.setAssessmentDoctor(assessmentDTO.getAssesmentDoctor());
		a.setAssessmentClinic(assessmentDTO.getAssesmentClinic());
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTO(a), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
		Assessment assessment = assessmentService.findOne(id); 
		

		if (assessment != null) {
			assessmentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
	
}
