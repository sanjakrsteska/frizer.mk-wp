package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.exceptions.InvalidArgumentsException;
import mk.frizer.model.exceptions.InvalidUsernameOrPasswordException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final BaseUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(BaseUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseUser login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        BaseUser user = userRepository.findByEmail(username)
                .orElseThrow(InvalidUsernameOrPasswordException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }

        return user;
    }

    @Override
    public List<BaseUser> findAll() {
        return userRepository.findAll();
    }
}

