package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Clinic;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClinicDTORes {
	
	private Integer id; 
	private String address; 
	private String name; 
	private String description;
	
	public ClinicDTORes(Clinic clinic) {
		this.id = clinic.getId(); 
		this.address = clinic.getAddress(); 
		this.name = clinic.getName(); 
		this.description = clinic.getDescription();
	}

}
