package uns.ac.rs.ib.security.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Assessment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssessmentDTORes {
	
	private Integer id;
	private Integer assesmentClinic; 
	private Integer assesmentDoctor; 

	public AssessmentDTORes(Assessment assessment) {
		this.id = assessment.getId();
		this.assesmentClinic = assessment.getAssessmentClinic(); 
		this.assesmentDoctor = assessment.getAssessmentDoctor();
	}
}
