package zatribune.spring.example.jms.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zatribune.spring.example.jms.db.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
