package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.HealthSer;

public interface HealthSerService {
	
	////////////////////////basic crud methods 
	List<HealthSer> findAll();

	HealthSer findOne(Long id);

	HealthSer save(HealthSer healthSer);

	void remove(Long id);	
	///////////////////////////////////////////

	
}
