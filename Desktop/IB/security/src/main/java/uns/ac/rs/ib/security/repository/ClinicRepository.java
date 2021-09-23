package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.dto.ClinicDTORes;
import uns.ac.rs.ib.security.model.Clinic;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

//    @Query("SELECT c from Clinic c where c._name like %:name%")
    List<Clinic> findByNameIgnoreCaseContaining(String name);

    Clinic findOneById(Integer id);
}
