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

import uns.ac.rs.ib.security.dto.AssessmentDTORes;
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
	public ResponseEntity<List<AssessmentDTORes>> getAssessments() {
		List<Assessment> assessments = assessmentService.findAll(); 
		List<AssessmentDTORes> assessmentsDTO = new ArrayList<>();
		for(Assessment a : assessments) {
			assessmentsDTO.add(new AssessmentDTORes(a));
		}
		return new ResponseEntity<List<AssessmentDTORes>>(assessmentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AssessmentDTORes> getAssessment(@PathVariable("id") Integer id) {
		Assessment a = assessmentService.findOne(id); 
		if(a == null) {
			return new ResponseEntity<AssessmentDTORes>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AssessmentDTORes>(new AssessmentDTORes(a), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<AssessmentDTORes> saveAssessment(@RequestBody AssessmentDTORes adto) {
		Assessment a = new Assessment(); 
		a.setAssessmentDoctor(adto.getAssesmentDoctor());
		a.setAssessmentClinic(adto.getAssesmentClinic());
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTORes(a), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
	public ResponseEntity<AssessmentDTORes> updateClinic(@RequestBody AssessmentDTORes assessmentDTO, @PathVariable("id") Long id) {
		
		Assessment a = new Assessment(); 
		a.setAssessmentDoctor(assessmentDTO.getAssesmentDoctor());
		a.setAssessmentClinic(assessmentDTO.getAssesmentClinic());
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTORes(a), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAssessment(@PathVariable("id") Integer id) {
		Assessment assessment = assessmentService.findOne(id); 
		

		if (assessment != null) {
			assessmentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
	
}
