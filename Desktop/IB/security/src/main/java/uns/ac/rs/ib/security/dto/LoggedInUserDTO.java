package uns.ac.rs.ib.security.dto;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUserDTO {
	
	private int id;
	private String token;
	private String username;
	private String email;
	private boolean changePass;
	private Collection<? extends GrantedAuthority> authorities;

}
