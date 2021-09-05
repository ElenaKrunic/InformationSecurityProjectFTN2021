package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.Assessment;

public interface AssessmentService {

	////////////////////////basic crud methods 
	List<Assessment> findAll();

	Assessment findOne(Integer id);

	Assessment save(Assessment assessment);

	void remove(Integer id);
	///////////////////////////////////////////



}
