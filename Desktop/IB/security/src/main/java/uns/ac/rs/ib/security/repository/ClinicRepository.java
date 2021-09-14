package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.dto.ClinicDTORes;
import uns.ac.rs.ib.security.model.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    Clinic findOneById(Integer id);
}
