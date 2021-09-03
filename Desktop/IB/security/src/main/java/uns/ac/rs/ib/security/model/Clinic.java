package uns.ac.rs.ib.security.model;

import lombok.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="clinic")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Clinic implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "_address", nullable = false)
    private String address;

    @Column(name = "_name", nullable = false)
    private String name;

    @Column(name = "_description")
    private String description;

    @OneToMany(mappedBy="clinic")
    private List<User> users;

    @OneToMany(mappedBy = "clinic")
    private List<HealthSer> healthSers;
}
