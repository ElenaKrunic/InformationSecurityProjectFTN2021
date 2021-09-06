package uns.ac.rs.ib.security.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.dto.ExaminationDTO;
import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.repository.UserRepository;
import uns.ac.rs.ib.security.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {
	
	@Autowired
	ExaminationRepository examinationRepository; 
	
	@Autowired
	UserRepository userRepository; 

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
	public List<ExaminationDTO> nursesWorkCalendar(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		
		if(user == null) {
			throw new Exception("User does not exist!"); 
		}
		
		List<ExaminationDTO> examinationsDTO = new ArrayList<>();
		List<Examination> examinations = examinationRepository.findAllByMedicalSisterAndPatientNotNull(user);
		
		for(Examination e : examinations) {
			if(e.getDate().before(new Date())) {
				continue;
			}
			
			ExaminationDTO dto = new ExaminationDTO();
			dto.setClinicName(e.getDoctor().getClinic().getName());
			dto.setDataAboutExamination(e.getDataAboutExamination());
			dto.setDate(e.getDate().toString());
			dto.setDiscount(e.getDiscount());
			dto.setDoctor(e.getDoctor().getFirstname());
			dto.setDuration(e.getDuration());
			dto.setPatient(e.getPatient().getFirstname());
			dto.setServiceName(e.getHealthSer().getName());
			dto.setServicePrice(e.getHealthSer().getPrice());
			
			examinationsDTO.add(dto);
		}
		return examinationsDTO;
	}

}
