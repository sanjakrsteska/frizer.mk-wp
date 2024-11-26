package mk.frizer.repository;

import mk.frizer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByBaseUser_Email(String email);
    Optional<Customer> findByBaseUserId(Long id);
}
