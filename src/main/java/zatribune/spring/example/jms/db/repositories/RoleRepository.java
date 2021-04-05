package zatribune.spring.example.jms.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zatribune.spring.example.jms.db.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
