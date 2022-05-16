package zatribune.example.activemq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zatribune.example.activemq.db.entities.User;
import zatribune.example.activemq.db.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user;
    }

    @Override
    public void save(User user) {
        //todo:
    }

    @Override
    public void saveToDefaults(User user) {
        //todo:
    }
}
