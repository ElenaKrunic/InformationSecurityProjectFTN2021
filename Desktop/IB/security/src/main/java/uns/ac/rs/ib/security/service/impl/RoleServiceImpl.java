package uns.ac.rs.ib.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.model.Role;
import uns.ac.rs.ib.security.repository.RoleRepository;
import uns.ac.rs.ib.security.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findOne(Integer id) {
		return roleRepository.getById(id);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void remove(Integer id) {
		roleRepository.deleteById(id);
	} 
}
