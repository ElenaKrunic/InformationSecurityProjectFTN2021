package uns.ac.rs.ib.security.service.impl;

import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.model.Clinic;
import uns.ac.rs.ib.security.model.Role;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.ClinicRepository;
import uns.ac.rs.ib.security.repository.RoleRepository;
import uns.ac.rs.ib.security.repository.UserRepository;
import uns.ac.rs.ib.security.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	ClinicRepository clinicRepository;

//	@Autowired
//	SecurityConfiguration configuration;

	@Autowired
	RoleRepository rolesRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findOne(Integer id) {
		return userRepository.getById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void remove(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String registerClinicAdmin(UserDTORequest userDtoRequest) throws Exception {
		User user = userRepository.findByEmail(userDtoRequest.getEmail());
		if(user!=null){
			throw new Exception("Korisnik vec postoji");
		}
		Optional<Clinic> clinicOptional = clinicRepository.findById(userDtoRequest.getIdClinic());
		if(!clinicOptional.isPresent()){
			throw new Exception("Klinika ne postoji");
		}
		user = new User();
		user.setAddress(userDtoRequest.getAddress());
		user.setEmail(userDtoRequest.getEmail());
		user.setFirstname(userDtoRequest.getFirstname());
		user.setLastname(userDtoRequest.getLastname());
		user.setIdentifier(userDtoRequest.getId());
		user.setValidated((byte)1);
		user.setPhoneNumber(userDtoRequest.getPhone());
//		user.setPass(configuration.passwordEncoder().encode(userDtoRequest.getPass()));
		user.setClinic(clinicOptional.get());
		user.setRoles(rolesRepository.findAllByName("KLINIKA_ADMIN"));
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		date = calendar.getTime();
		user.setExpire(date);
		userRepository.save(user);
		return "Clinic is successful added";
	}

	@Override
	public List<UserDTOResponse> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTOResponse> response = new ArrayList<>();
		for(User user:users){
			UserDTOResponse tmp = new UserDTOResponse();
			tmp.setId(user.getId());
			tmp.setEmail(user.getEmail());
			tmp.setFirstname(user.getFirstname());
			tmp.setIdentifier(user.getIdentifier());
			tmp.setPass(user.getPass());
			tmp.setLastname(user.getLastname());
			tmp.setPhone(user.getPhoneNumber());
			tmp.setValidate(user.getValidated());
			tmp.setAddress(user.getAddress());
			if(user.getClinic()!=null) {
				ClinicDTO clinicDto = new ClinicDTO();
				clinicDto.setName(user.getClinic().getName());
				clinicDto.setAddress(user.getClinic().getAddress());
				clinicDto.setId(user.getClinic().getId());
				clinicDto.setDescription(user.getClinic().getDescription());
				tmp.setClinicDto(clinicDto);
			}
			List<String> roles = new ArrayList<>();
			for(Role role:user.getRoles()){
				roles.add(role.getName());
			}
			tmp.setRoles(roles);
			response.add(tmp);
		}
		return response;
	}

	@Override
	public String validate(int id) throws Exception {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new Exception("This user doesn't exists");
		}
		User user = userOptional.get();
		user.setValidated((byte) 0);
		userRepository.save(user);
		return "Successful";
	}

	@Override
	public String unvalidate(int id) throws Exception {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new Exception("This user doesn't exists");
		}
		User user = userOptional.get();
		user.setValidated((byte) 1);
		userRepository.save(user);
		return "Successful";
	}

	@Override
	public String editProfile(UserDTOEdit userDTOEdit, String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if(user==null){
			throw new Exception("This user doesn't exists");
		}
		user.setAddress(userDTOEdit.getAddress());
		user.setFirstname(userDTOEdit.getFirstname());
		user.setLastname(userDTOEdit.getLastname());
		user.setPhoneNumber(userDTOEdit.getPhone());
		userRepository.save(user);
		return "Successful";
	}

	@Override
	public UserDTOResponse getMyProfile(String name) throws Exception {
		User user = userRepository.findByEmail(name);
		if(user==null){
			throw new Exception("This user doesn't exists");
		}
		UserDTOResponse tmp = new UserDTOResponse();
		tmp.setId(user.getId());
		tmp.setEmail(user.getEmail());
		tmp.setFirstname(user.getFirstname());
		tmp.setIdentifier(user.getIdentifier());
		tmp.setPass(user.getPass());
		tmp.setLastname(user.getLastname());
		tmp.setPhone(user.getPhoneNumber());
		tmp.setValidate(user.getValidated());
		tmp.setAddress(user.getAddress());
		if(user.getClinic()!=null) {
			ClinicDTO clinicDTO = new ClinicDTO();
			clinicDTO.setName(user.getClinic().getName());
			clinicDTO.setAddress(user.getClinic().getAddress());
			clinicDTO.setId(user.getClinic().getId());
			clinicDTO.setDescription(user.getClinic().getDescription());
			tmp.setClinicDto(clinicDTO);
		}
		return tmp;
	}

	@Override
	public void changePassword(ChangePasswordDTO changePasswordDto, Principal principal) throws Exception {

	}
}
