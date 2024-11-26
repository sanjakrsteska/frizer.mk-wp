package mk.frizer.repository;

import mk.frizer.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findByName(String name);
    Optional<City> findByNameEqualsIgnoreCase(String name);
    @Query("SELECT c FROM City c WHERE SIZE(c.salonsInCity) > 0 ORDER BY SIZE(c.salonsInCity) DESC")
    List<City> findTopCities(Pageable pageable);
}
