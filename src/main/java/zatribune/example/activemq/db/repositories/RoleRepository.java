package zatribune.example.activemq.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zatribune.example.activemq.db.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
