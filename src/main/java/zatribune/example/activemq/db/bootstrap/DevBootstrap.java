package zatribune.example.activemq.db.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import zatribune.example.activemq.db.entities.Role;
import zatribune.example.activemq.db.repositories.RoleRepository;
import zatribune.example.activemq.db.repositories.UserRepository;
import zatribune.example.activemq.db.entities.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DevBootstrap(UserRepository userRepository, RoleRepository roleRepository) {
        log.debug("I'm at the Bootstrap phase");
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(@Nullable ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }


    void initData() {
        log.info("bootstrap data");
        Role role1 =new Role();
        role1.setName("ADMIN");

        Role role2 =new Role();
        role2.setName("USER");

        roleRepository.saveAll(Arrays.asList(role1, role2));
        User user1 =new User();
        user1.setUsername("user1@gmail.com");
        //strength 12 and input= 'pass'
        user1.setPassword("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user1.setPasswordConfirm("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user1.setRoles(new HashSet<>(Collections.singletonList(role1)));
        user1.setAccountNonExpired(Boolean.TRUE);
        user1.setAccountNonLocked(Boolean.TRUE);
        user1.setCredentialsNotExpired(Boolean.TRUE);
        user1.setEnabled(Boolean.TRUE);


        User user2 =new User();
        user2.setUsername("user2@gmail.com");
        //strength 12 and input= 'pass'
        user2.setPassword("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user2.setPasswordConfirm("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user2.setRoles(new HashSet<>(Collections.singletonList(role2)));
        user2.setAccountNonExpired(Boolean.TRUE);
        user2.setAccountNonLocked(Boolean.TRUE);
        user2.setCredentialsNotExpired(Boolean.TRUE);
        user2.setEnabled(Boolean.TRUE);

        User user3 =new User();
        user3.setUsername("user3@gmail.com");
        //strength 12 and input= 'pass'
        user3.setPassword("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user3.setPasswordConfirm("$2y$12$bwUGNLEwKKGA/CleGetPIOfJGGZsB9Ymj0KOmJirJ/pn/wdSQVfie");
        user3.setRoles(new HashSet<>(Collections.singletonList(role2)));
        user3.setAccountNonExpired(Boolean.TRUE);
        user3.setAccountNonLocked(Boolean.TRUE);
        user3.setCredentialsNotExpired(Boolean.TRUE);
        user3.setEnabled(Boolean.TRUE);

        userRepository.saveAll(List.of(user1, user2,user3));
    }
}
