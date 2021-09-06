package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.MedicalRecord;
import uns.ac.rs.ib.security.model.User;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    List<MedicalRecord> findAllByExaminationPatient(User user);
}
