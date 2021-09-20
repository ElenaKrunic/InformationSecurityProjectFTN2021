package uns.ac.rs.ib.security.service.impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import uns.ac.rs.ib.security.dto.DiagnosisDTO;
import uns.ac.rs.ib.security.dto.MedicalRecordDTO;
import uns.ac.rs.ib.security.dto.MedicialRecordDTOs;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.repository.MedicalRecordRepository;
import uns.ac.rs.ib.security.repository.UserRepository;
import uns.ac.rs.ib.security.service.MedicalRecordService;

import javax.crypto.Cipher;
import javax.xml.bind.JAXB;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

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
//		if(!nurse && !doctor){
//			throw new Exception("You do not have the right to inspect the card!");
//		}
		List<MedicialRecordDTOs> response = new ArrayList<>();
		List<MedicalRecord> logovi = medicalRecordRepository.findAllByExaminationPatient(patient);
		for (MedicalRecord mr : logovi) {
			MedicialRecordDTOs tmp = new MedicialRecordDTOs();
			tmp.setId(mr.getId());
			tmp.setCertified(mr.getCertified());
			tmp.setTime(mr.getTime());
			tmp.setTherapy(mr.getTherapy());

			String dijagnoza = mr.getNote();
			if (dijagnoza != null && !dijagnoza.isEmpty()) {
				String desifrovanaDijagnoza = desifrujTekst(dijagnoza);
				System.out.println(desifrovanaDijagnoza);
				tmp.setDisease(getTagValue(desifrovanaDijagnoza, "disease"));
				tmp.setNote(getTagValue(desifrovanaDijagnoza, "note"));
			}
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
//		if (!examination.getDoctor().getEmail().equals(name)) {
//			throw new Exception("You are not a doctor to this patient!!");
//		}
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

		DiagnosisDTO encryptedDiagnosis = new DiagnosisDTO(medicalRecordDTOReq.getDisease(), medicalRecordDTOReq.getNote());

		StringWriter sw = new StringWriter();
		JAXB.marshal(encryptedDiagnosis, sw);
		String xmlString = sw.toString();
		String xmlEncoded = sifrujTekst(xmlString);
		medicalRecord.setNote(xmlEncoded);

//		medicalRecord.setNote(medicalRecord.getNote());


		medicalRecordRepository.save(medicalRecord);
		return "Successful!!";
	}

	@Override
	public String verify(int id, String email) throws Exception {
		User user = userRepository.findByEmail(email); 
		
		if(user == null) {
			throw new Exception("User does not exist!"); 
		}
		
		Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id); 
		
		if(medicalRecord.isEmpty()) {
			throw new Exception("Medical record does not exist!"); 
		}
		
		MedicalRecord md = medicalRecord.get(); 
		
		if(md.getExamination().getMedicalSister().getId() != user.getId()) {
			throw new Exception("You are not allowed to verify this medical record!"); 
		}
		
		md.setCertified((byte)1);
		medicalRecordRepository.save(md);
		return "Verified!";
	}

	@Override
	public List<MedicialRecordDTOs> myRecord(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("Korisnik ne postoji");
		}
		List<MedicialRecordDTOs> response = new ArrayList<>();
		List<MedicalRecord> logovi = medicalRecordRepository.findAllByExaminationPatient(user);
		for (MedicalRecord zk : logovi) {
			MedicialRecordDTOs tmp = new MedicialRecordDTOs();
			tmp.setId(zk.getId());
			tmp.setCertified(zk.getCertified());
			tmp.setTime(zk.getTime());
			tmp.setTherapy(zk.getTherapy());

			String dijagnoza = zk.getNote();
			if (dijagnoza != null && !dijagnoza.isEmpty()) {
				String desifrovanaDijagnoza = desifrujTekst(dijagnoza);
				System.out.println(desifrovanaDijagnoza);
				tmp.setDisease(getTagValue(desifrovanaDijagnoza, "disease"));
				tmp.setNote(getTagValue(desifrovanaDijagnoza, "note"));
			}
			response.add(tmp);
		}
		return response;
	}

	public String sifrujTekst(String tekst) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		Resource resource = loadPublicKeyFile();
//		File publicKeyFile = new File(resource.getURI());
		File publicKeyFile = new File("C:\\Users\\Svetozar\\git\\InformationSecurityProject_FTN_2021\\Desktop\\IB\\security\\src\\main\\resources\\cert\\public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] secretMessageBytes = tekst.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

		String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
		return encodedMessage;
	}

	public String desifrujTekst(String tekst) throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		File privateKeyFile = new File("C:\\Users\\Svetozar\\git\\InformationSecurityProject_FTN_2021\\Desktop\\IB\\security\\src\\main\\resources\\cert\\private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] encryptedMessageBytes2 = Base64.getDecoder().decode(tekst);
		byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes2);
		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

		return decryptedMessage;
	}
}
