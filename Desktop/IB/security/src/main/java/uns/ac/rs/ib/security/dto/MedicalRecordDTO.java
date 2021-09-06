package uns.ac.rs.ib.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalRecordDTO {
    private String disease;

    private String note;

    private String therapy;

    private int idExamination;
}
