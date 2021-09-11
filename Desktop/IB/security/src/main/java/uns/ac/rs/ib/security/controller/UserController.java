package uns.ac.rs.ib.security.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.security.TokenUtils;
import uns.ac.rs.ib.security.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;
    
    
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login (@RequestBody LoginDTO lDTO){
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(lDTO.getUsername(), lDTO.getPassword());
            UserDetails details = userDetailsService.loadUserByUsername(lDTO.getUsername());
            System.out.println("details"+details);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return new ResponseEntity<String>("Login failed", HttpStatus.BAD_REQUEST);
        }
	}
	
	
    
    /*
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
                user.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = userDetailsService.loadUserByUsername(user.getUsername());
        User userDb = userService.findByEmail(user.getUsername());
        LoggedInUserDTO loggedIn = new LoggedInUserDTO(userDb.getId(), tokenUtils.generateToken(details),
                userDb.getEmail(), details.getUsername(), details.getAuthorities());
        if (userDb.getValidiran() != 1) {
            return new ResponseEntity<>(new SimpleStringResponseDTO("Nalog nije aktivan"), HttpStatus.BAD_REQUEST);
        }
        if (userDb.getExpire()!=null && userDb.getExpire().before(new Date())){
            return new ResponseEntity<>(new SimpleStringResponseDTO("Vreme za promenu passworda isteklo"), HttpStatus.BAD_REQUEST);
        }
        if(userDb.getExpire()!=null){
            loggedIn.setChangePass(true);
        }
        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
    }
    */
    
    @PostMapping("/register-clinic-center-admin")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<?> registerClinicCenterAdmin(@RequestBody UserDTORequest userDTORequest) {
    	try {
    		String register = userService.registerClinicCenterAdmin(userDTORequest);
    		return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PostMapping("/register-clinic-admin")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<?> registerClinicAdmin(@RequestBody UserDTORequest userDTORequest) {
        try {
            String mess = userService.registerClinicAdmin(userDTORequest);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @PostMapping("/register-doctor")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
    public ResponseEntity<?> registerDoctor(@RequestBody UserDTORequest userDTORequest) {
    	try {
    		String register = userService.registerDoctor(userDTORequest);
    		return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PostMapping("/register-nurse")
    @PreAuthorize("hasAuthority('CLINIC_ADMIN')")
    public ResponseEntity<?> registerNurse(@RequestBody UserDTORequest userDTORequest) {
    	try {
    		String register = userService.registerNurse(userDTORequest);
    		return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PostMapping("/register-patient")
    //@PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<?> registerPatient(@RequestBody UserDTORequest userDTORequest) {
    	try {
    		String register = userService.registerPatient(userDTORequest);
    		return new ResponseEntity<>(new StringResponseDTO(register), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<List<UserDTOResponse>> findAll() {
        List<UserDTOResponse> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/validate/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<?> ban(@PathVariable("id") Integer id) {
        try {
            String mess = userService.validate(id);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/unvalidate/{id}")
    @PreAuthorize("hasAuthority('CLINIC_CENTER_ADMIN')")
    public ResponseEntity<?> unban(@PathVariable("id") Integer id) {
        try {
            String mess = userService.unvalidate(id);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('PATIENT') || hasAuthority('NURSE') || hasAuthority('DOCTOR') ")
    public ResponseEntity<?> editProfile(@RequestBody UserDTOEdit userDTORequest, Principal principal) {
        try {
            String poruka = userService.editProfile(userDTORequest, principal.getName());
            return new ResponseEntity<>(new StringResponseDTO(poruka), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {
        try {
            UserDTOResponse user = userService.getMyProfile(principal.getName());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<StringResponseDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDto, Principal principal) {
        try {
            userService.changePassword(changePasswordDto, principal);
            return new ResponseEntity<>(new StringResponseDTO("Successful!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
        
}
