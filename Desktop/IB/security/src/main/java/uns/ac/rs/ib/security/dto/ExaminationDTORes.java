package uns.ac.rs.ib.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExaminationDTORes {
    private int id;
    private String dataOfExamination;
    private int discount;
    private int during;
    private String time;
    private String nameOfService;
    private int price;
    private String doctor;
    private String nameClinic;
    private String addressClinic;
    private String patient;
}
