package zatribune.example.activemq.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import zatribune.example.activemq.db.entities.User;

public interface UserService extends UserDetailsService {
    void save(User user);
    void saveToDefaults(User user);
}
