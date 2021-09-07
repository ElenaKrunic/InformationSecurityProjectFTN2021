package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.Clinic;
import uns.ac.rs.ib.security.model.HealthSer;

import java.util.List;

@Repository
public interface HealthSerRepository extends JpaRepository<HealthSer, Integer> {

    List<HealthSer> findAllByClinic(Clinic clinic);
}
