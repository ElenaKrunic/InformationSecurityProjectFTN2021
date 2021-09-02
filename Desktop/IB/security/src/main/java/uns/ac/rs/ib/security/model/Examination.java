package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="examination")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Examination implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "data_about_examination", nullable = false)
    private String dataAboutExamination;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "_date", nullable = false)
    private Date date;
}
