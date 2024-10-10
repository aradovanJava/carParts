package hr.carparts.store.carpartsstore.repository;

import hr.carparts.store.carpartsstore.model.CarPart;
import hr.carparts.store.carpartsstore.model.CarPartCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface SpringDataJpaCarPartStoreRepository extends JpaRepository<CarPart, Integer> {
    CarPart findByNameAndCategoryAndPriceAndDescription(
            String name, CarPartCategory category, BigDecimal price, String description);

    List<CarPart> findByNameContainingIgnoreCase(String name);
}
