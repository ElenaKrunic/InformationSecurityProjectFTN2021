package uns.ac.rs.ib.security.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.dto.ExaminationDTOReq;
import uns.ac.rs.ib.security.dto.ExaminationDTORes;
import uns.ac.rs.ib.security.dto.SimpleSelectDTORes;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.model.Role;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.repository.HealthSerRepository;
import uns.ac.rs.ib.security.repository.UserRepository;
import uns.ac.rs.ib.security.service.EmailService;
import uns.ac.rs.ib.security.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {
	
	@Autowired
	ExaminationRepository examinationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HealthSerRepository healthSerRepository;

//	@Autowired
//	EmailService emailService;

	@Override
	public List<Examination> findAll() {
		return examinationRepository.findAll();
	}

	@Override
	public Examination findOne(Integer id) {
		return examinationRepository.getById(id);
	}

	@Override
	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}

	@Override
	public void remove(Integer id) {
		examinationRepository.deleteById(id);
	}

	@Override
	public List<ExaminationDTORes> historyExamination(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("User doesn't exists");
		}
		List<ExaminationDTORes> response = new ArrayList<>();
		List<Examination> examinations = examinationRepository.findAllByPatient(user);
		for (Examination e : examinations) {
			ExaminationDTORes tmp = new ExaminationDTORes();
			tmp.setId(e.getId());
			tmp.setDataOfExamination(e.getDataAboutExamination());
			tmp.setDiscount(e.getDiscount());
			tmp.setDuring(e.getDuration());
			tmp.setTime(e.getDate().toString());
			tmp.setNameOfService(e.getHealthSer().getName());
			tmp.setPrice(e.getHealthSer().getPrice());
			tmp.setDoctor(e.getDoctor().getFirstname() + " " + e.getDoctor().getLastname());
			tmp.setNameClinic(e.getDoctor().getClinic().getName());
			tmp.setAddressClinic(e.getDoctor().getClinic().getAddress());
			response.add(tmp);
		}
		return response;
	}

	@Override
	public List<ExaminationDTORes> availableTerms(String name) {
		List<ExaminationDTORes> response = new ArrayList<>();
		List<Examination> examinations = examinationRepository.findAllByPatientIsNull();
		for (Examination e : examinations) {
			ExaminationDTORes tmp = new ExaminationDTORes();
			tmp.setId(e.getId());
			tmp.setDataOfExamination(e.getDataAboutExamination());
			tmp.setDiscount(e.getDiscount());
			tmp.setDuring(e.getDuration());
			tmp.setTime(e.getDate().toString());
			tmp.setNameOfService(e.getHealthSer().getName());
			tmp.setPrice(e.getHealthSer().getPrice());
			tmp.setDoctor(e.getDoctor().getFirstname() + " " + e.getDoctor().getLastname());
			tmp.setNameClinic(e.getDoctor().getClinic().getName());
			tmp.setAddressClinic(e.getDoctor().getClinic().getAddress());
			response.add(tmp);
		}
		return response;
	}

	@Override
	public String createExamination(int id, String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("User doesn't exists");
		}

		Optional<Examination> examinationOptional = examinationRepository.findById(id);
		if (!examinationOptional.isPresent()) {
			throw new Exception("Appointment doesn't exists");
		}
		Examination examination = examinationOptional.get();
		if (examination.getPatient() != null) {
			throw new Exception("Appointment doesn't exists");
		}
		examination.setPatient(user);
		examinationRepository.save(examination);
//		emailService.scheduleExamination(examination.getDoctor().getClinic().getName(), examination.getHealthSer().getName(), examination.getDate().toString(), examination.getDoctor().getFirstname() + " " + examination.getDoctor().getLastname(), user.getEmail());
		return "Success";
	}

	@Override
	public String createAppointment(ExaminationDTOReq examinationDTOReq, String name) throws Exception {
		User clinicAdmin = userRepository.findByEmail(name);
		if (clinicAdmin == null) {
			throw new Exception("User doesn't exists");
		}
		Optional<User> doctorOpt = userRepository.findById(examinationDTOReq.getDoctorId());
		if(!doctorOpt.isPresent()){
			throw new Exception("Doctor doesn't exists");
		}
		Optional<User> nurseOpt = userRepository.findById(examinationDTOReq.getNurseId());
		if(!nurseOpt.isPresent()){
			throw new Exception("Nurse doesn't exists");
		}
		User doctor = doctorOpt.get();
		User nurse = nurseOpt.get();
		if(clinicAdmin.getClinic().getId() != doctor.getClinic().getId()){
			throw new Exception("The doctor is not from your clinic");
		}
		if(clinicAdmin.getClinic().getId() != nurse.getClinic().getId()){
			throw new Exception("The nurse is not from your clinic");
		}
		Optional<HealthSer> serviceOpt = healthSerRepository.findById(examinationDTOReq.getServiceId());
		if(!serviceOpt.isPresent()){
			throw new Exception("Service doesn't exists");
		}
		HealthSer usluga = serviceOpt.get();
		if(usluga.getClinic().getId() != clinicAdmin.getClinic().getId()){
			throw new Exception("The service is not from your clinic");
		}



		Examination examination = new Examination();
		examination.setDoctor(doctor);
		examination.setDataAboutExamination(examinationDTOReq.getDataOfExamination());
		examination.setHealthSer(usluga);
		examination.setDiscount(examinationDTOReq.getDiscount());
		examination.setMedicalSister(nurse);
		examination.setDuration(examinationDTOReq.getDuring());

		String timeExamination = examinationDTOReq.getTime().replace("T", " ");
		SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date time=formatter6.parse(timeExamination);
		examination.setDate(time);

		examinationRepository.save(examination);
		return "Success";
	}

	@Override
	public List<SimpleSelectDTORes> doctors(String name) throws Exception {
		User clinicAdmin = userRepository.findByEmail(name);
		if (clinicAdmin == null) {
			throw new Exception("User doesn't exists");
		}
		List<SimpleSelectDTORes> response = new ArrayList<>();
		List<User> usersForClinic = userRepository.findAllByClinic(clinicAdmin.getClinic());
		for(User u:usersForClinic){
			for(Role r:u.getRoles()){
				if(r.getName().equals("LEKAR")){
					SimpleSelectDTORes tmp = new SimpleSelectDTORes(u.getId(), u.getFirstname() + " " + u.getLastname());
					response.add(tmp);
					break;
				}
			}
		}
		return response;
	}

	@Override
	public List<SimpleSelectDTORes> nurses(String name) throws Exception {
		User clinicAdmin = userRepository.findByEmail(name);
		if (clinicAdmin == null) {
			throw new Exception("User doesn't exists");
		}
		List<SimpleSelectDTORes> response = new ArrayList<>();
		List<User> usersForClinic = userRepository.findAllByClinic(clinicAdmin.getClinic());
		for(User u:usersForClinic){
			for(Role r:u.getRoles()){
				if(r.getName().equals("MEDICINSKA_SESTRA")){
					SimpleSelectDTORes tmp = new SimpleSelectDTORes(u.getId(), u.getFirstname() + " " + u.getLastname());
					response.add(tmp);
					break;
				}
			}
		}
		return response;
	}

	@Override
	public List<SimpleSelectDTORes> services(String name) throws Exception {
		User clinicAdmin = userRepository.findByEmail(name);
		if (clinicAdmin == null) {
			throw new Exception("User doesn't exists");
		}
		List<SimpleSelectDTORes> response = new ArrayList<>();
		List<HealthSer> serviceForClinic = healthSerRepository.findAllByClinic(clinicAdmin.getClinic());
		for(HealthSer u:serviceForClinic){
			SimpleSelectDTORes tmp = new SimpleSelectDTORes(u.getId(), u.getName() + " " + u.getPrice() + "din");
			response.add(tmp);
		}

		return response;
	}

	@Override
	public List<ExaminationDTORes> workCalendar(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("User doesn't exists");
		}
		List<ExaminationDTORes> response = new ArrayList<>();
		List<Examination> examinations = examinationRepository.findAllByDoctorAndPatientIsNotNull(user);
		for (Examination e : examinations) {
			if(e.getDate().before(new Date())){
				continue;
			}
			ExaminationDTORes tmp = new ExaminationDTORes();
			tmp.setId(e.getId());
			tmp.setDataOfExamination(e.getDataAboutExamination());
			tmp.setDiscount(e.getDiscount());
			tmp.setDuring(e.getDuration());
			tmp.setTime(e.getDate().toString());
			tmp.setNameOfService(e.getHealthSer().getName());
			tmp.setPrice(e.getHealthSer().getPrice());
			tmp.setDoctor(e.getDoctor().getFirstname() + " " + e.getDoctor().getLastname());
			tmp.setNameClinic(e.getDoctor().getClinic().getName());
			tmp.setAddressClinic(e.getDoctor().getClinic().getAddress());
			tmp.setPatient(e.getPatient().getFirstname() + " " + e.getPatient().getLastname() + " " +e.getPatient().getIdentifier());

			response.add(tmp);
		}
		return response;
	}

	@Override
	public List<ExaminationDTORes> workCalendarForNurse(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if (user == null) {
			throw new Exception("User doesn't exists");
		}
		List<ExaminationDTORes> response = new ArrayList<>();
		List<Examination> examinations = examinationRepository.findAllByMedicalSisterAndPatientIsNotNull(user);
		for (Examination e : examinations) {
			if(e.getDate().before(new Date())){
				continue;
			}
			ExaminationDTORes tmp = new ExaminationDTORes();
			tmp.setId(e.getId());
			tmp.setDataOfExamination(e.getDataAboutExamination());
			tmp.setDiscount(e.getDiscount());
			tmp.setDuring(e.getDuration());
			tmp.setTime(e.getDate().toString());
			tmp.setNameOfService(e.getHealthSer().getName());
			tmp.setPrice(e.getHealthSer().getPrice());
			tmp.setDoctor(e.getDoctor().getFirstname() + " " + e.getDoctor().getLastname());
			tmp.setNameClinic(e.getDoctor().getClinic().getName());
			tmp.setAddressClinic(e.getDoctor().getClinic().getAddress());
			tmp.setPatient(e.getPatient().getFirstname() + " " + e.getPatient().getLastname() + " " +e.getPatient().getIdentifier());

			response.add(tmp);
		}
		return response;
	}

}
