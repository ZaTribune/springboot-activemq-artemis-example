package zatribune.example.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService{

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public boolean isAuthenticated() {
        //Obtains the currently authenticated principal, or an authentication request token.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /* *
        SecurityContext: An interface defining the minimum security
        information associated with the current thread of execution.
        SecurityContextHolder: Associates a given SecurityContext with the current execution thread.
           This class provides a series of static methods that delegate to an instance of
           SecurityContextHolderStrategy. The purpose of the class is to provide a
           convenient way to specify the strategy that should
           be used for a given JVM. This is a JVM-wide setting, since everything in this class is
           static to facilitate ease of use in calling code.*/

        //weather it's anonymous or null
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        /*Attempts to authenticate the passed link Authentication object, returning a
        fully populated Authentication object (including granted authorities)
        if successful.*/
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            // Token -> Authentication -> AuthenticationManager -> SecurityContext
            //propagate the created authentication into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            log.info(String.format("Auto login for user [ %s ] successfully!", username));
        }
    }
}
