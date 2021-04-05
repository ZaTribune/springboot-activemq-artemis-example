package zatribune.spring.example.jms.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
