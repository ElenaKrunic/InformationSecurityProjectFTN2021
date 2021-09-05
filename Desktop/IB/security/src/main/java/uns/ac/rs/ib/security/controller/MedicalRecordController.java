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

import uns.ac.rs.ib.security.dto.MedicalRecordDTO;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.service.MedicalRecordService;

@RestController
@RequestMapping(value = "/api/medicalRecords")
public class MedicalRecordController {
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	
	@GetMapping(value="/all")
	public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecords() {
		List<MedicalRecord> medicalRecords = medicalRecordService.findAll(); 
		List<MedicalRecordDTO> medicalRecordsDTO = new ArrayList<>();
		for(MedicalRecord mr : medicalRecords) {
			medicalRecordsDTO.add(new MedicalRecordDTO(mr));
		}
		
		return new ResponseEntity<List<MedicalRecordDTO>>(medicalRecordsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		if(medicalRecord == null) {
			return new ResponseEntity<MedicalRecordDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalRecordDTO>(new MedicalRecordDTO(medicalRecord), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<MedicalRecordDTO> saveMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
		MedicalRecord medicalRecord = new MedicalRecord(); 
		medicalRecord.setCertified(medicalRecordDTO.getCertified());
		medicalRecord.setNote(medicalRecordDTO.getNote());
		medicalRecord.setTherapy(medicalRecordDTO.getTherapy());
		medicalRecord.setTime(medicalRecordDTO.getTime());
		
		medicalRecord = medicalRecordService.save(medicalRecord); 
		
		return new ResponseEntity<MedicalRecordDTO>(new MedicalRecordDTO(medicalRecord), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes="application/json", value="/{id}")
	public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO, @PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		medicalRecord.setCertified(medicalRecordDTO.getCertified());
		medicalRecord.setNote(medicalRecordDTO.getNote());
		medicalRecord.setTherapy(medicalRecordDTO.getTherapy());
		medicalRecord.setTime(medicalRecordDTO.getTime());
		
		medicalRecord = medicalRecordService.save(medicalRecord); 
		
		return new ResponseEntity<MedicalRecordDTO>(new MedicalRecordDTO(medicalRecord), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		if(medicalRecord != null) {
			medicalRecordService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	
}
