package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.Role;

public interface RoleService {
	
	////////////////////////basic crud methods 
	List<Role> findAll();

	Role findOne(Integer id);

	Role save(Role role);

	void remove(Integer id);
	///////////////////////////////////////////


}
