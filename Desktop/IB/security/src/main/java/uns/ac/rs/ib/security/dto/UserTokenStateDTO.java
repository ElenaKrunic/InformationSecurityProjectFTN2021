package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserTokenStateDTO {

	 private String accessToken;
	 private Long expiresIn;
	 
	 public UserTokenStateDTO(String accessToken, long expiresIn) {
	        this.accessToken = accessToken;
	        this.expiresIn = expiresIn;
	    }
}
