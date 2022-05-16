package zatribune.example.activemq.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zatribune.example.activemq.db.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
