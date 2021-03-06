package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uns.ac.rs.ib.security.dto.RoleDTO;
import uns.ac.rs.ib.security.model.Role;
import uns.ac.rs.ib.security.service.RoleService;

@RestController
@RequestMapping(value="/api/roles")
@CrossOrigin
public class RoleController {

	@Autowired
	RoleService roleService; 
	
	@GetMapping(value="/all")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<List<RoleDTO>> getRoles(){
		List<Role> roles = roleService.findAll();
		List<RoleDTO> rolesDTO = new ArrayList<>();
		for(Role r: roles) {
			rolesDTO.add(new RoleDTO(r)); 
		}
		
		return new ResponseEntity<List<RoleDTO>>(rolesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<RoleDTO> getRole(@PathVariable("id") Integer id) {
		Role role = roleService.findOne(id);
		if(role == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RoleDTO>(new RoleDTO(role), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
		Role role = new Role(); 
		role.setName(roleDTO.getName());
		
		role = roleService.save(role); 
		return new ResponseEntity<RoleDTO>(new RoleDTO(role), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = "application/json")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO, @PathVariable("id") Integer id) {
		 
		Role role = roleService.findOne(id); 
		
		if(role == null) {
			return new ResponseEntity<RoleDTO>(HttpStatus.BAD_REQUEST);
		}
		
		role.setName(roleDTO.getName());
		
		role = roleService.save(role);
		
		return new ResponseEntity<>(new RoleDTO(role), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
	public ResponseEntity<Void> deleteRole(@PathVariable("id") Integer id) {
		Role role = roleService.findOne(id); 
		
		if(role != null) {
			roleService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
