package mk.frizer.service;

import mk.frizer.model.BaseUser;
import mk.frizer.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    BaseUser register(String email, String password, String repeatPassword, String firstName, String lastName, String phoneNumber);
}
