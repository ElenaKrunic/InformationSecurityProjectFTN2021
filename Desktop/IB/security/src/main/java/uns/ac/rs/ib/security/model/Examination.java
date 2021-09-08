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

    @Column(name = "_discount")
    private Integer discount;

    @Column(name = "_duration")
    private Integer duration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "_date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name="doctor")
    private User doctor;

    @ManyToOne
    @JoinColumn(name="medical_sister")
    private User medicalSister;

    @ManyToOne
    @JoinColumn(name="patient")
    private User patient;

    @ManyToOne
    private HealthSer healthSer;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.LAZY)
    private Assessment assessment;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL,  orphanRemoval = true)
    private MedicalRecord medicalRecord;
}
