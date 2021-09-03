package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.Examination;

public interface ExaminationService {
	
	////////////////////////basic crud methods 
	List<Examination> findAll(); 
	
	Examination findOne(Long id); 
	
	Examination save(Examination examination); 
	
	void remove(Long id);
	///////////////////////////////////////////
}
