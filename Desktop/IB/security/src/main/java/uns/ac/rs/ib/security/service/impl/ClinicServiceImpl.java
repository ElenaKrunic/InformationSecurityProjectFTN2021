package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

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

}
