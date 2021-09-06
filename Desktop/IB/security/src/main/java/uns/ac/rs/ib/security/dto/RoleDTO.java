package uns.ac.rs.ib.security.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {

	    private int id;
	    private String name;
	    
	    public RoleDTO(Role role) {
	    	this.id = role.getId(); 
	    	this.name = role.getName();
	    }
}
