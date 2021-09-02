package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long>{

}
