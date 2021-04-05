package zatribune.spring.example.jms.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zatribune.spring.example.jms.db.repositories.UserRepository;
import zatribune.spring.example.jms.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        //strength the log rounds to use, between 4 and 31
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;



    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository=userRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        This takes /h2-console out of Spring Security’s authorization,
        so you don’t have to log in to the app before you can log in to the database.
        Then it turns off CSRF{Cross-Site Request Forgery} only for /h2-console. CSRF protection breaks all POSTs until
        you add the secret formula to each form. The console doesn’t have these,
        so it is not compatible with CSRF protection.
        Then it enables loading pages in frames as long as the frames come from our own site.
        This affects the entire application.
        Now the console will work when you’re running your app locally,
        and the rest of your site will be secure as before.*/

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**","/webjars/**","/svg/**", "/registration","/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin()
                .and().authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


}