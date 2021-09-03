package uns.ac.rs.ib.security.service;

import java.util.List;

import uns.ac.rs.ib.security.model.User;

public interface UserService {

	////////////////////////basic crud methods 
	List<User> findAll();

	User findOne(Long id);

	User save(User user);
	
	void remove(Long id);	

	///////////////////////////////////////////

}
