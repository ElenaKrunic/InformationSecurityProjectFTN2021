package uns.ac.rs.ib.security.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.repository.MedicalRecordRepository;
import uns.ac.rs.ib.security.repository.UserRepository;
import uns.ac.rs.ib.security.service.MedicalRecordService;

import javax.xml.bind.JAXB;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{

	@Autowired 
	MedicalRecordRepository medicalRecordRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ExaminationRepository examinationRepository;

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

	@Override
	public List<MedicialRecordDTOs> recordOfPatient(int id, String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("User doesn't exists!");
		}
		Optional<User> patientOpt = userRepository.findById(id);
		if (!patientOpt.isPresent()) {
			throw new Exception("User doesn't exists!");
		}
		User patient = patientOpt.get();
		List<Examination> examinationsForNurseAndPatient = examinationRepository.findAllByMedicalSisterAndPatient(user, patient);
		List<Examination> examinationsForDoctorAndPatient = examinationRepository.findAllByDoctorAndPatient(user, patient);
		boolean doctor = true;
		if (examinationsForDoctorAndPatient == null || examinationsForDoctorAndPatient.isEmpty()) {
			doctor = false;
		}
		boolean nurse = true;
		if (examinationsForNurseAndPatient == null || examinationsForNurseAndPatient.isEmpty()) {
			nurse = false;
		}
		if(!nurse && !doctor){
			throw new Exception("You do not have the right to inspect the card!");
		}
		List<MedicialRecordDTOs> response = new ArrayList<>();
		List<MedicalRecord> logovi = medicalRecordRepository.findAllByExaminationPatient(patient);
		for (MedicalRecord mr : logovi) {
			MedicialRecordDTOs tmp = new MedicialRecordDTOs();
			tmp.setId(mr.getId());
			tmp.setCertified(mr.getCertified());
			tmp.setTime(mr.getTime());
			tmp.setTherapy(mr.getTherapy());

			response.add(tmp);
		}
		return response;
	}

	public static String getTagValue(String xml, String tagName) {
		return xml.split("<" + tagName + ">")[1].split("</" + tagName + ">")[0];
	}

	@Override
	public String addNote(MedicalRecordDTO medicalRecordDTOReq, String name) throws Exception {
		Optional<Examination> examinationOptional = examinationRepository.findById(medicalRecordDTOReq.getIdExamination());
		if (!examinationOptional.isPresent()) {
			throw new Exception("Examination doesn't exists!");
		}
		Examination examination = examinationOptional.get();
		if (!examination.getDoctor().getEmail().equals(name)) {
			throw new Exception("You are not a doctor to this patient!!");
		}
		if (medicalRecordDTOReq.getDisease() == null) {
			throw new Exception("Disease is a mandatory field!");
		}

		if (medicalRecordDTOReq.getNote() == null) {
			throw new Exception("Note is a mandatory field!");
		}
		MedicalRecord medicalRecord = new MedicalRecord();
		if (examination.getMedicalRecord() != null) {
			medicalRecord = examination.getMedicalRecord();
		}

		medicalRecord.setTherapy(medicalRecordDTOReq.getTherapy());
		medicalRecord.setExamination(examination);
		medicalRecord.setTime(new Date());

		medicalRecordRepository.save(medicalRecord);
		return "Successful!!";
	}
}
