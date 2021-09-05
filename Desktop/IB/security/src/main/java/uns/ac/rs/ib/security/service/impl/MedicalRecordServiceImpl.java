package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.repository.MedicalRecordRepository;
import uns.ac.rs.ib.security.service.MedicalRecordService;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{

	@Autowired 
	MedicalRecordRepository medicalRecordRepository;

	@Override
	public List<MedicalRecord> findAll() {
		return medicalRecordRepository.findAll();
	}

	@Override
	public MedicalRecord findOne(Integer id) {
		return medicalRecordRepository.getById(id);
	}

	@Override
	public MedicalRecord save(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	@Override
	public void remove(Integer id) {
		medicalRecordRepository.deleteById(id);
		
	} 
}
