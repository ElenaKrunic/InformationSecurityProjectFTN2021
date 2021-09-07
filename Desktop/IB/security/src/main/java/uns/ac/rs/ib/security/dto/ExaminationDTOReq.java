package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExaminationDTOReq {

    private String dataOfExamination;
    private int discount;
    private int during;
    private String time;
    private Integer doctorId;
    private Integer nurseId;
    private Integer serviceId;
}
