package zatribune.spring.example.jms.service;

import zatribune.spring.example.jms.db.entities.User;

public interface UserService {
    void save(User user);
    void saveToDefaults(User user);
    User findByUsername(String username);
}
