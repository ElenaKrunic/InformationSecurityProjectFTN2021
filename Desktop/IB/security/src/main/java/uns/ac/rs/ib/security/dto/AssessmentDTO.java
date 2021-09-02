package uns.ac.rs.ib.security.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssessmentDTO {
	
	private Integer id;
	
	private Integer assesmentClinic; 
	
	private Integer assesmentDoctor; 

}
