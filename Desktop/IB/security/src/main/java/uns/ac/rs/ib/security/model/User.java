package uns.ac.rs.ib.security.model;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "identifier", nullable = true)//, nullable = false
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

    @OneToMany(mappedBy="doctor")
    private List<Examination> examinationsForDoctor;

    @OneToMany(mappedBy="medicalSister")
    private List<Examination> examinationsForMedicalSister;

    @OneToMany(mappedBy="patient")
    private List<Examination> examinationsForPatient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_has_user"
            , joinColumns={
            @JoinColumn(name="user_id")
    }
            , inverseJoinColumns={
            @JoinColumn(name="role_id")
    }
    )
    private List<Role> roles;

    @ManyToOne
    private Clinic clinic;

    public Examination addExaminationsForDoctor(Examination examinationsForDoctor) {
        getExaminationsForDoctor().add(examinationsForDoctor);
        examinationsForDoctor.setDoctor(this);

        return examinationsForDoctor;
    }

    public Examination removeExaminationsForDoctor(Examination examinationsForDoctor) {
        getExaminationsForDoctor().remove(examinationsForDoctor);
        examinationsForDoctor.setDoctor(null);

        return examinationsForDoctor;
    }

    public Examination addExaminationsForMedicalSister(Examination examinationsForMedicalSister) {
        getExaminationsForMedicalSister().add(examinationsForMedicalSister);
        examinationsForMedicalSister.setMedicalSister(this);

        return examinationsForMedicalSister;
    }

    public Examination removeExaminationsForMedicalSister(Examination examinationsForMedicalSister) {
        getExaminationsForMedicalSister().remove(examinationsForMedicalSister);
        examinationsForMedicalSister.setMedicalSister(null);

        return examinationsForMedicalSister;
    }

    public Examination addExaminationsForPatient(Examination examinationsForPatient) {
        getExaminationsForPatient().add(examinationsForPatient);
        examinationsForPatient.setPatient(this);

        return examinationsForPatient;
    }
    public Examination removeExaminationsForPatient(Examination examinationsForPatient) {
        getExaminationsForPatient().remove(examinationsForPatient);
        examinationsForPatient.setPatient(null);

        return examinationsForPatient;
    }
    
    
    @JsonIgnore
    public String getRolesAsString() {
    	StringBuilder sb = new StringBuilder();
    	
    	for (Role role : this.roles) {
    		sb.append(role.getName() + " ");
    	}
    	
    	return sb.toString();
    }

}