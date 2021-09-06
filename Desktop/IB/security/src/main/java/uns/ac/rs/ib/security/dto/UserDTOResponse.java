package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTOResponse implements Comparable<UserDTOResponse>{

    private int id;

    private String address;

    private String email;

    private String identifier;

    private String firstname;

    private String pass;

    private String lastname;

    private String phone;

    private byte validate;

    private ClinicDTORes clinicDto;

    private List<String> roles;

    @Override
    public int compareTo(UserDTOResponse userDtoRes) {
        return this.getFirstname().compareTo((userDtoRes).getFirstname());
    }
}
