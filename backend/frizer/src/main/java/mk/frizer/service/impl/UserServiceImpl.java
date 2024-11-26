package mk.frizer.service.impl;

import mk.frizer.model.BaseUser;
import mk.frizer.model.Customer;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.exceptions.*;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import mk.frizer.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final BaseUserRepository baseUserRepository;
    private final CustomerRepository customerRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, BaseUserRepository baseUserRepository, CustomerRepository customerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.baseUserRepository = baseUserRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BaseUser register(String email, String password,String repeatPassword, String firstName, String lastName, String phoneNumber) {
        BaseUserAddDTO baseUserAddDTO = new BaseUserAddDTO(email, password, firstName, lastName, phoneNumber);
        if(this.baseUserRepository.findByEmail(email).isPresent()) {
            throw new UsernameAlreadyExistsException(email);
        }

        BaseUser user = baseUserRepository.save(new BaseUser(baseUserAddDTO.getEmail(), passwordEncoder.encode(baseUserAddDTO.getPassword()), baseUserAddDTO.getFirstName(), baseUserAddDTO.getLastName(), baseUserAddDTO.getPhoneNumber()));
        Customer customer = customerRepository.save(new Customer(user));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return baseUserRepository.findByEmail(username)
                .orElse(null);
    }

}
