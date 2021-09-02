package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.Clinic;

public interface ClinicService {

	List<Clinic> findAll();

	Clinic findOne(Long id);

	Clinic save(Clinic clinic);

	void remove(Long id);


}
