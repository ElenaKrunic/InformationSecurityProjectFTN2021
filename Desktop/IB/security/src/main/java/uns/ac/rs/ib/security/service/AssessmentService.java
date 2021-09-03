package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.Assessment;

public interface AssessmentService {

	////////////////////////basic crud methods 
	List<Assessment> findAll();

	Assessment findOne(Long id);

	Assessment save(Assessment assessment);

	void remove(Long id);
	///////////////////////////////////////////



}
