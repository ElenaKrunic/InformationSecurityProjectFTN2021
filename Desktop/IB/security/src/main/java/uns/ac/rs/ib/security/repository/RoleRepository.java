package uns.ac.rs.ib.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uns.ac.rs.ib.security.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
