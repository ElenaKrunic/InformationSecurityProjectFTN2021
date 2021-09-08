package uns.ac.rs.ib.security.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExaminationDTO {

    private Integer id;
    private String dataAboutExamination;
    private Integer discount;
    private Integer duration;
    private Date date;
    private String serviceName; 
    private Integer servicePrice; 
    private String doctor; 
    private String patient;
    private String clinicName; 
    
    
    public ExaminationDTO(Examination examination) {
    	this.id = examination.getId(); 
    	this.dataAboutExamination = examination.getDataAboutExamination(); 
    	this.discount = examination.getDiscount(); 
    	this.duration = examination.getDuration(); 
    	this.date = examination.getDate();
    	this.serviceName = examination.getHealthSer().getName();
    	this.servicePrice = examination.getHealthSer().getPrice();
    	this.doctor = examination.getDoctor().getFirstname() + ' ' + examination.getDoctor().getLastname(); 
    	this.patient = examination.getPatient().getFirstname() + ' ' +  examination.getPatient().getLastname(); 
    	this.clinicName = examination.getDoctor().getClinic().getName();
    }
    
    public ExaminationDTO(Integer id) {
    	this.id = id;
    }
    
    
}
