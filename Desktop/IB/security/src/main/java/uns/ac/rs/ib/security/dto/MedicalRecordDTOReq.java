package uns.ac.rs.ib.security.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.MedicalRecord;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecordDTOReq {

	private byte certified;
	private String therapy;
	private String note;
	private Date time;
	
	public MedicalRecordDTOReq(MedicalRecord mr) { 
		  this.certified = mr.getCertified(); 
		  this.therapy = mr.getTherapy(); 
		  this.note = mr.getNote(); 
		  this.time = mr.getTime();
	}
}
