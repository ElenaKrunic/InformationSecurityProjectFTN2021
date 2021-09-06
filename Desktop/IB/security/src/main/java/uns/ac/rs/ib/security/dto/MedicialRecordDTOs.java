package uns.ac.rs.ib.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicialRecordDTOs {

    private Integer id;
    private String disease;
    private byte certified;
    private String therapy;
    private String note;
    private Date time;
}
