package uns.ac.rs.ib.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.Clinic;
import uns.ac.rs.ib.security.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {

    User findByEmail(String username);
    List<User> findAllByClinic(Clinic clinic);
}
