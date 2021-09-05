package uns.ac.rs.ib.security.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uns.ac.rs.ib.security.dto.*;
import uns.ac.rs.ib.security.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register-clinic-admin")
    public ResponseEntity<?> registerClinicAdmin(@RequestBody UserDTORequest userDTORequest) {
        try {
            String mess = userService.registerClinicAdmin(userDTORequest);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTOResponse>> findAll() {
        List<UserDTOResponse> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<?> ban(@PathVariable("id") Integer id) {
        try {
            String mess = userService.validate(id);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/unvalidate/{id}")
    public ResponseEntity<?> unban(@PathVariable("id") Integer id) {
        try {
            String mess = userService.unvalidate(id);
            return new ResponseEntity<>(new StringResponseDTO(mess), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new StringResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit")
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
