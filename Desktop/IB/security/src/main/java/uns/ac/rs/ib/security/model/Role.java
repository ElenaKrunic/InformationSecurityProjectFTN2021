package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy="roles", fetch = FetchType.EAGER)
    private List<User> users;

	@Override
	public String getAuthority() {
		return name;
	}
    
    
}
