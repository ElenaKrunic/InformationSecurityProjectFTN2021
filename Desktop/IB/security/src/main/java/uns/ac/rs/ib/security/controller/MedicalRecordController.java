package uns.ac.rs.ib.security.controller;

import java.security.Principal;
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
import uns.ac.rs.ib.security.dto.MedicalRecordDTORes;
import uns.ac.rs.ib.security.dto.MedicialRecordDTOs;
import uns.ac.rs.ib.security.dto.StringResponseDTO;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.service.MedicalRecordService;

@RestController
@RequestMapping(value = "/api/medicalRecords")
public class MedicalRecordController {
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	
	@GetMapping(value="/all")
	public ResponseEntity<List<MedicalRecordDTORes>> getMedicalRecords() {
		List<MedicalRecord> medicalRecords = medicalRecordService.findAll(); 
		List<MedicalRecordDTORes> medicalRecordsDTO = new ArrayList<>();
		for(MedicalRecord mr : medicalRecords) {
			medicalRecordsDTO.add(new MedicalRecordDTORes(mr));
		}
		
		return new ResponseEntity<List<MedicalRecordDTORes>>(medicalRecordsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<MedicalRecordDTORes> getMedicalRecord(@PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		if(medicalRecord == null) {
			return new ResponseEntity<MedicalRecordDTORes>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalRecordDTORes>(new MedicalRecordDTORes(medicalRecord), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<MedicalRecordDTORes> saveMedicalRecord(@RequestBody MedicalRecordDTORes medicalRecordDTO) {
		MedicalRecord medicalRecord = new MedicalRecord(); 
		medicalRecord.setCertified(medicalRecordDTO.getCertified());
		medicalRecord.setNote(medicalRecordDTO.getNote());
		medicalRecord.setTherapy(medicalRecordDTO.getTherapy());
		medicalRecord.setTime(medicalRecordDTO.getTime());
		
		medicalRecord = medicalRecordService.save(medicalRecord); 
		
		return new ResponseEntity<MedicalRecordDTORes>(new MedicalRecordDTORes(medicalRecord), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes="application/json", value="/{id}")
	public ResponseEntity<MedicalRecordDTORes> updateMedicalRecord(@RequestBody MedicalRecordDTORes medicalRecordDTO, @PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		medicalRecord.setCertified(medicalRecordDTO.getCertified());
		medicalRecord.setNote(medicalRecordDTO.getNote());
		medicalRecord.setTherapy(medicalRecordDTO.getTherapy());
		medicalRecord.setTime(medicalRecordDTO.getTime());
		
		medicalRecord = medicalRecordService.save(medicalRecord); 
		
		return new ResponseEntity<MedicalRecordDTORes>(new MedicalRecordDTORes(medicalRecord), HttpStatus.CREATED);
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

	@GetMapping("/record-patient/{id}")
	public ResponseEntity<?> recordPatient(@PathVariable("id") int id, Principal principal) {
		try {
			List<MedicialRecordDTOs> logs = medicalRecordService.recordOfPatient(id, "milica@gmail.com");
			return new ResponseEntity<>(logs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/add-note")
	public ResponseEntity<?> addNote(@RequestBody MedicalRecordDTO medicalRecordDTO, Principal principal) {
		try {
			String message = medicalRecordService.addNote(medicalRecordDTO, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/verify/{id}")
	public ResponseEntity<?> verify(@PathVariable("id") int id, Principal principal) {
		try {
			String verify = medicalRecordService.verify(id, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(verify), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
