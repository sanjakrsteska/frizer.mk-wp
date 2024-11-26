package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.BaseUser;
import mk.frizer.model.Customer;
import mk.frizer.model.dto.BaseUserAddDTO;
import mk.frizer.model.dto.BaseUserUpdateDTO;
import mk.frizer.model.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.service.BaseUserService;
import mk.frizer.utilities.FormValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public BaseUserServiceImpl(BaseUserRepository baseUserRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.baseUserRepository = baseUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<BaseUser> getBaseUsers() {
        return baseUserRepository.findAll();
    }

    @Override
    public Optional<BaseUser> getBaseUserById(Long id) {
        BaseUser user = baseUserRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<BaseUser> createBaseUser(BaseUserAddDTO baseUserAddDTO) {
        BaseUser user = baseUserRepository.save(new BaseUser(baseUserAddDTO.getEmail(), passwordEncoder.encode(baseUserAddDTO.getPassword()), baseUserAddDTO.getFirstName(), baseUserAddDTO.getLastName(), baseUserAddDTO.getPhoneNumber()));
        Customer customer = customerRepository.save(new Customer(user));
        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<BaseUser> updateBaseUser(Long id, BaseUserUpdateDTO baseUserUpdateDTO) {
        BaseUser user = getBaseUserById(id).get();

        if (FormValidator.isNameValid(baseUserUpdateDTO.getFirstName())) {
            user.setFirstName(baseUserUpdateDTO.getFirstName());
        }
        if (FormValidator.isNameValid(baseUserUpdateDTO.getLastName())) {
            user.setLastName(baseUserUpdateDTO.getLastName());
        }
        if (FormValidator.isPhoneNumberValid(baseUserUpdateDTO.getPhoneNumber())) {
            user.setPhoneNumber(baseUserUpdateDTO.getPhoneNumber());
        }

        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> changeBaseUserPassword(Long id, String password) {
        BaseUser user = getBaseUserById(id).get();
        user.setPassword(password);
        return Optional.of(baseUserRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<BaseUser> deleteBaseUserById(Long id) {
        //try catch?
        BaseUser user = getBaseUserById(id).get();
        baseUserRepository.deleteById(id);
        return Optional.of(user);
    }
}
