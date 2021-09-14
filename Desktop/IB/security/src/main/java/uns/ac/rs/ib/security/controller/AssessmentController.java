package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uns.ac.rs.ib.security.dto.AssessmentDTORes;
import uns.ac.rs.ib.security.model.Assessment;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.repository.AssessmentRepository;
import uns.ac.rs.ib.security.service.AssessmentService;
import uns.ac.rs.ib.security.service.ExaminationService;

@RestController
@RequestMapping(value = "/api/assessments")
@CrossOrigin
public class AssessmentController {

	@Autowired
	AssessmentService assessmentService; 
	
	@Autowired
	AssessmentRepository assessmentRepository; 
	
	@Autowired
	ExaminationService examinationService; 
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<List<AssessmentDTORes>> getAssessments() {
		List<Assessment> assessments = assessmentService.findAll(); 
		List<AssessmentDTORes> assessmentsDTO = new ArrayList<>();
		for(Assessment a : assessments) {
			assessmentsDTO.add(new AssessmentDTORes(a));
		}
		return new ResponseEntity<List<AssessmentDTORes>>(assessmentsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<AssessmentDTORes> getAssessment(@PathVariable("id") Integer id) {
		Assessment a = assessmentService.findOne(id); 
		if(a == null) {
			return new ResponseEntity<AssessmentDTORes>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<AssessmentDTORes>(new AssessmentDTORes(a), HttpStatus.OK);
	}
	
	//exc int after, when fe incl.  
	//one to one problem
	@PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<AssessmentDTORes> saveAssessment(@RequestBody AssessmentDTORes adto){
		Assessment a = new Assessment(); 
		Examination examination = examinationService.findOne(adto.getExaminationDTO().getId());
		//Examination examination = examinationService.findOne(id);
		a.setAssessmentDoctor(adto.getAssessmentDoctor());
		a.setAssessmentClinic(adto.getAssessmentClinic());
		
		a.setExamination(examination);
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTORes(a), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<AssessmentDTORes> updateAssessment(@RequestBody AssessmentDTORes assessmentDTO, @PathVariable("id") int id) {
		
		Assessment a = assessmentService.findOne(id); 
		a.setAssessmentDoctor(assessmentDTO.getAssessmentDoctor());
		a.setAssessmentClinic(assessmentDTO.getAssessmentClinic());
		
		a = assessmentService.save(a); 
		return new ResponseEntity<> (new AssessmentDTORes(a), HttpStatus.CREATED);
	}
	
	//one to one pa se brise i examination
	@DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<Void> deleteAssessment(@PathVariable("id") Integer id) {
		Assessment assessment = assessmentService.findOne(id); 
		

		if (assessment != null) {
			assessmentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);		
	}
	
}
