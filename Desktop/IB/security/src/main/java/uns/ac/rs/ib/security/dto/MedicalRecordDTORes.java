package uns.ac.rs.ib.security.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.MedicalRecord;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecordDTORes {

	private Integer id;
    private byte certified;
	private String therapy;
	private String note;
	private Date time;

	  public MedicalRecordDTORes(MedicalRecord mr) {
		  this.id = mr.getId(); 
		  this.certified = mr.getCertified(); 
		  this.therapy = mr.getTherapy(); 
		  this.note = mr.getNote(); 
		  this.time = mr.getTime();
	}
}
