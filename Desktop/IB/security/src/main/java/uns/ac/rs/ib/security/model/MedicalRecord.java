package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="medical_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecord implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "certified", nullable = false)
    private byte certified;

    @Column(name = "therapy", nullable = false)
    private String therapy;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "time", nullable = false)
    private Date time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "examination_id", referencedColumnName = "id")
    private Examination examination;
}
