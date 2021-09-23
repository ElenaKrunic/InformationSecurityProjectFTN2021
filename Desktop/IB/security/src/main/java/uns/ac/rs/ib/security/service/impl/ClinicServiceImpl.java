package uns.ac.rs.ib.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.dto.ClinicDTORes;
import uns.ac.rs.ib.security.model.Clinic;
import uns.ac.rs.ib.security.repository.ClinicRepository;
import uns.ac.rs.ib.security.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	ClinicRepository clinicRepository; 
	
	@Override
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}

	@Override
	public Clinic findOne(Integer id) {
		return clinicRepository.getById(id);
	}

	@Override
	public Clinic save(Clinic clinic) {
		return clinicRepository.save(clinic);
	}

	@Override
	public void remove(Integer id) {
		 clinicRepository.deleteById(id);
	}

	@Override
	public List<ClinicDTORes> findByName(String name) {
		List<Clinic> clinics = clinicRepository.findByNameIgnoreCaseContaining(name);
		List<ClinicDTORes> response = new ArrayList<>();
		for(Clinic c: clinics){
			ClinicDTORes cdto = new ClinicDTORes();
			cdto.setId(c.getId());
			cdto.setAddress(c.getAddress());
			cdto.setName(c.getName());
			cdto.setDescription(c.getDescription());
			response.add(cdto);
		}
		return response;
	}

}
