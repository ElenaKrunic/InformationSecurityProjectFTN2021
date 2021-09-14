package uns.ac.rs.ib.security.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uns.ac.rs.ib.security.dto.MedicalRecordDTO;
import uns.ac.rs.ib.security.dto.MedicalRecordDTORes;
import uns.ac.rs.ib.security.dto.MedicialRecordDTOs;
import uns.ac.rs.ib.security.dto.StringResponseDTO;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.service.MedicalRecordService;

@RestController
@RequestMapping(value = "/api/medicalRecords")
@CrossOrigin
public class MedicalRecordController {
	
	@Autowired
	MedicalRecordService medicalRecordService; 
	
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<List<MedicalRecordDTORes>> getMedicalRecords() {
		List<MedicalRecord> medicalRecords = medicalRecordService.findAll(); 
		List<MedicalRecordDTORes> medicalRecordsDTO = new ArrayList<>();
		for(MedicalRecord mr : medicalRecords) {
			medicalRecordsDTO.add(new MedicalRecordDTORes(mr));
		}
		
		return new ResponseEntity<List<MedicalRecordDTORes>>(medicalRecordsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<MedicalRecordDTORes> getMedicalRecord(@PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		if(medicalRecord == null) {
			return new ResponseEntity<MedicalRecordDTORes>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<MedicalRecordDTORes>(new MedicalRecordDTORes(medicalRecord), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
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
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
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
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable("id") Integer id) {
		MedicalRecord medicalRecord = medicalRecordService.findOne(id);
		
		if(medicalRecord != null) {
			medicalRecordService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/record-patient/{id}")
    @PreAuthorize("hasAuthority('DOCTOR') || hasAuthority('NURSE')")
	public ResponseEntity<?> recordPatient(@PathVariable("id") int id) {//, Principal principal
		try {
			List<MedicialRecordDTOs> logs = medicalRecordService.recordOfPatient(id, "jevrosimatajna@gmail.com");
			return new ResponseEntity<>(logs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/add-note")
    @PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<?> addNote(@RequestBody MedicalRecordDTO medicalRecordDTO) {//, Principal principal
		try {
			String message = medicalRecordService.addNote(medicalRecordDTO, "jevrosimatajna@gmail.com");
			return new ResponseEntity<>(new StringResponseDTO(message), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/verify/{id}")
    @PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<?> verify(@PathVariable("id") int id, Principal principal) {
		try {
			String verify = medicalRecordService.verify(id, principal.getName());
			return new ResponseEntity<>(new StringResponseDTO(verify), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


		}
	}

	@GetMapping("/my-record")
    @PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<?> mojKarton(Principal principal) {
		try {
			List<MedicialRecordDTOs> logovi = medicalRecordService.myRecord("lelekrunic1@gmail.com");
			return new ResponseEntity<>(logovi, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
