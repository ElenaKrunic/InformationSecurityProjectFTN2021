package uns.ac.rs.ib.security.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Assessment;

@NoArgsConstructor
@Getter
@Setter
public class AssessmentDTORes {
	
	private Integer id;
	private Integer assessmentClinic; 
	private Integer assessmentDoctor; 
	private ExaminationDTO examinationDTO;

	/*
	public AssessmentDTORes(Assessment assessment) {
		this.id = assessment.getId();
		this.assessmentClinic = assessment.getAssessmentClinic(); 
		this.assessmentDoctor = assessment.getAssessmentDoctor();
	}
	*/
	
	public AssessmentDTORes(Assessment assessment) {
		this(assessment.getId(), assessment.getAssessmentClinic(), assessment.getAssessmentDoctor(), new ExaminationDTO(assessment.getExamination()));
	}
	
	public AssessmentDTORes(Integer id, Integer assessmentClinic, Integer assessmentDoctor, ExaminationDTO examinationDTO) {
		this.id = id; 
		this.assessmentClinic = assessmentClinic; 
		this.assessmentDoctor = assessmentDoctor; 
		this.examinationDTO = examinationDTO;
	}

}
