package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.Assessment;
import uns.ac.rs.ib.security.repository.AssessmentRepository;
import uns.ac.rs.ib.security.service.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService{

	@Autowired
	AssessmentRepository assessmentRepository;
	
	@Override
	public List<Assessment> findAll() {
		return assessmentRepository.findAll();
	}

	@Override
	public Assessment findOne(Integer id) {
		return assessmentRepository.getById(id);
	}

	@Override
	public Assessment save(Assessment assessment) {
		return assessmentRepository.save(assessment);
	}

	@Override
	public void remove(Integer id) {
		assessmentRepository.deleteById(id);
	}

}
