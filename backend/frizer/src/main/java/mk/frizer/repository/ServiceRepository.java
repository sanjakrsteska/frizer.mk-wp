package mk.frizer.repository;

import mk.frizer.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Treatment, Long> {
}
