package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.dto.MedicalRecordDTO;
import uns.ac.rs.ib.security.dto.MedicalRecordDTOReq;
import uns.ac.rs.ib.security.dto.MedicalRecordDTORes;
import uns.ac.rs.ib.security.dto.MedicialRecordDTOs;
import uns.ac.rs.ib.security.model.MedicalRecord;

public interface MedicalRecordService {

	////////////////////////basic crud methods 
	List<MedicalRecord> findAll();

	MedicalRecord findOne(Integer id);
	
	MedicalRecord save(MedicalRecord medicalRecord);

	void remove(Integer id);
	///////////////////////////////////////////

	List<MedicialRecordDTOs> recordOfPatient(int id, String name) throws Exception;

	String addNote(MedicalRecordDTO medicalRecordDTOReq, String name) throws Exception;
	
	String verify(int id, String email) throws Exception;

	List<MedicialRecordDTOs> myRecord(String name) throws Exception;

}
