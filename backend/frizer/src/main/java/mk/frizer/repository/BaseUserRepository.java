package mk.frizer.repository;

import mk.frizer.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    Optional<BaseUser> findByEmail(String username);
    Optional<BaseUser> findByEmailAndPassword(String username, String password);
}
