package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.MedicalRecord;

public interface MedicalRecordService {

	////////////////////////basic crud methods 
	List<MedicalRecord> findAll();

	MedicalRecord findOne(Long id);
	
	MedicalRecord save(MedicalRecord medicalRecord);

	void remove(Long id);	
	///////////////////////////////////////////

}
