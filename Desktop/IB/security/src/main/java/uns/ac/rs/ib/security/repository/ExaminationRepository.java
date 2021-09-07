package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.Examination;
import uns.ac.rs.ib.security.model.User;

import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer>{
    List<Examination> findAllByPatient(User user);
    List<Examination> findAllByDoctorAndPatient(User doctor, User patient);
    List<Examination> findAllByMedicalSisterAndPatient(User doctor, User patient);

    List<Examination> findAllByDoctorAndPatientIsNotNull(User user);
    List<Examination> findAllByMedicalSisterAndPatientIsNotNull(User user);
    List<Examination> findAllByPatientIsNull();
}
