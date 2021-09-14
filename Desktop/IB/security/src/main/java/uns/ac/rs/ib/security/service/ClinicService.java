package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.dto.ClinicDTORes;
import uns.ac.rs.ib.security.model.Clinic;

public interface ClinicService {

	////////////////////////basic crud methods 
	List<Clinic> findAll();

	Clinic findOne(Integer id);

	Clinic save(Clinic clinic);

	void remove(Integer id);
	///////////////////////////////////////////


}
