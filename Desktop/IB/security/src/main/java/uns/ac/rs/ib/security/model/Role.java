package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "_name", nullable = false)
    private String name;
}
