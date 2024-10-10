package hr.carparts.store.carpartsstore.service;

import hr.carparts.store.carpartsstore.dto.CarPartDTO;
import hr.carparts.store.carpartsstore.model.CarPart;
import hr.carparts.store.carpartsstore.model.CarPartCategory;
import hr.carparts.store.carpartsstore.repository.SpringDataJpaCarPartCategoryRepository;
import hr.carparts.store.carpartsstore.repository.SpringDataJpaCarPartStoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarPartValidationServiceImpl {

    private SpringDataJpaCarPartStoreRepository carPartStorerepository;
    private SpringDataJpaCarPartCategoryRepository carPartCategoryRepository;

    public Boolean validateCarPartDtoModel(CarPartDTO carPartDTO) {
        CarPart carPart = carPartStorerepository.findByNameAndCategoryAndPriceAndDescription(
                carPartDTO.getName(),
                carPartCategoryRepository.findByName(carPartDTO.getCategoryString()).getFirst(),
                carPartDTO.getPrice(),
                carPartDTO.getDescription()
        );

        return Optional.ofNullable(carPart).isEmpty();
    }

}
