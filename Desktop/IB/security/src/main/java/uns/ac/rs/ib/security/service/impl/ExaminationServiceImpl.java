package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.repository.ExaminationRepository;
import uns.ac.rs.ib.security.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {
	
	@Autowired
	ExaminationRepository examinationRepository; 

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

}
