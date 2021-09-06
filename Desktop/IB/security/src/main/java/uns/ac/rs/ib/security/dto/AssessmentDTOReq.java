package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Assessment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssessmentDTOReq {

	private Integer assesmentClinic; 
	private Integer assesmentDoctor; 

	public AssessmentDTOReq(Assessment assessment) {
		this.assesmentClinic = assessment.getAssessmentClinic(); 
		this.assesmentDoctor = assessment.getAssessmentDoctor();
	}
}
