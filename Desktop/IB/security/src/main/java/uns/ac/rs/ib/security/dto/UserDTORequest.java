package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTORequest {

    private String id;

    private String pass;

    private String address;

    private String phone;

    private String firstname;

    private String lastname;

    private String email;

    private Integer idClinic;
    
    private String identifier;
}
