package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="assessment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Assessment implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name="assessment_clinic")
    private Integer assessmentClinic;

    @Column(name="assessment_doctor")
    private Integer assessmentDoctor;
}
