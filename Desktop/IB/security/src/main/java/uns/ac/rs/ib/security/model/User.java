package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "validated", nullable = false)
    private byte validated;

    @Column(name = "expire", nullable = false)
    private Date expire;
}
