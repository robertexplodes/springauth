package at.flo.springauth.repository;

import at.flo.springauth.domain.Role;
import at.flo.springauth.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(Roles roleName);

}
