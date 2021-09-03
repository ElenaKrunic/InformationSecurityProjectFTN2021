package uns.ac.rs.ib.security.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uns.ac.rs.ib.security.model.HealthSer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HealthSerDTO {

    private Integer id;
    private Integer price;
    private String name;
    
    public HealthSerDTO(HealthSer healthSer) {
    	this.id = healthSer.getId(); 
    	this.price = healthSer.getPrice(); 
    	this.name = healthSer.getName(); 
    }
}
