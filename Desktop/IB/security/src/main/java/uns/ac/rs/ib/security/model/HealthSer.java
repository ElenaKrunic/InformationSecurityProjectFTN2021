package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="health_service")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HealthSer implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "healthSer")
    private List<Examination> examinations;

    @ManyToOne
    private Clinic clinic;

    public Examination addExamination(Examination examination){
        getExaminations().add(examination);
        examination.setHealthSer(this);

        return examination;
    }

    public Examination removeExamination(Examination examination){
        getExaminations().remove(examination);
        examination.setHealthSer(null);

        return examination;
    }
}
