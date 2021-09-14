package uns.ac.rs.ib.security.service;

import java.security.Principal;
import java.util.List;

import uns.ac.rs.ib.security.dto.ChangePasswordDTO;
import uns.ac.rs.ib.security.dto.UserDTOEdit;
import uns.ac.rs.ib.security.dto.UserDTORequest;
import uns.ac.rs.ib.security.dto.UserDTOResponse;
import uns.ac.rs.ib.security.model.User;

public interface UserService {

	////////////////////////basic crud methods 
	List<User> findAll();

	User findOne(Integer id);

	User save(User user);
	
	void remove(Integer id);

	///////////////////////////////////////////

	User findByEmail(String username);

	String registerClinicAdmin(UserDTORequest userDtoRequest) throws Exception;

	List<UserDTOResponse> findAllUsers();

	String validate(int id) throws Exception;

	String unvalidate(int id) throws Exception;

	String editProfile(UserDTOEdit userDTOEdit, String name) throws Exception;

	UserDTOResponse getMyProfile(String name) throws Exception;

	void changePassword(ChangePasswordDTO changePasswordDto, Principal principal) throws Exception;
	
	String registerClinicCenterAdmin(UserDTORequest userDtoRequest) throws Exception;
	
	String registerDoctor(UserDTORequest userDtoRequest) throws Exception;
	
	String registerPatient(UserDTORequest userDtoRequest) throws Exception;
	
	String registerNurse(UserDTORequest userDtoRequest) throws Exception;
	

}
