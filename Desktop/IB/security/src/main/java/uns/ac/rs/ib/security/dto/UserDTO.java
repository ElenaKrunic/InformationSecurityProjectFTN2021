package uns.ac.rs.ib.security.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	    private int id;
	    private String address;
	    private String email;
	    private String identifier;
	    private String firstname;
	    private String pass;
	    private String lastname;
	    private String phoneNumber;
	    private byte validated;
	    private Date expire;
}
