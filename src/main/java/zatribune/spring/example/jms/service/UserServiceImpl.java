package zatribune.spring.example.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zatribune.spring.example.jms.db.entities.User;
import zatribune.spring.example.jms.db.repositories.RoleRepository;
import zatribune.spring.example.jms.db.repositories.UserRepository;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        //todo: check authorities
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public void saveToDefaults(User user) {
        //todo: check authorities
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        user.setEnabled(false);
        user.setAccountNonLocked(false);
        user.setAccountNonExpired(false);
        user.setCredentialsNotExpired(false);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
