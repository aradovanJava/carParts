package hr.carparts.store.carpartsstore.service;

import hr.carparts.store.carpartsstore.dto.CarPartDTO;
import hr.carparts.store.carpartsstore.model.CarPart;
import hr.carparts.store.carpartsstore.model.CarPartCategory;
import hr.carparts.store.carpartsstore.model.CarPartsSearchForm;
import hr.carparts.store.carpartsstore.model.nosql.NoSqlCarPart;
import hr.carparts.store.carpartsstore.repository.MongoDbCarPartRepository;
import hr.carparts.store.carpartsstore.repository.SpringDataJpaCarPartCategoryRepository;
import hr.carparts.store.carpartsstore.repository.SpringDataJpaCarPartStoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarPartsStoreServiceImpl implements CarPartsStoreService {

    private SpringDataJpaCarPartCategoryRepository carPartCategoryRepository;
    private SpringDataJpaCarPartStoreRepository carPartRepository;
    private MongoDbCarPartRepository mongoDbCarPartRepository;

    @Override
    public List<CarPartDTO> findAll() {
        return carPartRepository.findAll()
                .stream().map(this::convertCarPartToCarPartDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CarPartDTO> findById(Integer id) {
        return carPartRepository.findById(id).stream()
                .map(this::convertCarPartToCarPartDto)
                .findFirst();
    }

    @Override
    public void save(CarPartDTO newCarPart) {
        carPartRepository.save(convertCarPartDtoToCarPart(newCarPart));

        NoSqlCarPart noSqlCarPart = new NoSqlCarPart();
        noSqlCarPart.setCategory(newCarPart.getCategoryString());
        noSqlCarPart.setName(newCarPart.getName());
        noSqlCarPart.setPrice(newCarPart.getPrice());
        noSqlCarPart.setDescription(newCarPart.getDescription());

        mongoDbCarPartRepository.save(noSqlCarPart);
    }

    @Override
    public List<CarPartDTO> filterByCriteria(CarPartsSearchForm carPartsSearchForm) {

        List<NoSqlCarPart> carParts = new ArrayList<>();

        if(!carPartsSearchForm.getName().isEmpty()) {
            carParts.addAll(mongoDbCarPartRepository.findByNameIgnoreCaseLike(carPartsSearchForm.getName()));
        }

        CarPart filterCarPart = new CarPart();
        String carPartName = carPartsSearchForm.getName();
        filterCarPart.setName(carPartName);
        Example<CarPart> carPartExample = Example.of(filterCarPart);

        return carPartRepository.findAll(carPartExample)
                .stream()
                .map(this::convertCarPartToCarPartDto)
                .toList();
    }

    @Override
    public List<CarPartDTO> findByName(String name) {
        return carPartRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::convertCarPartToCarPartDto)
                .toList();
    }

    private CarPartDTO convertCarPartToCarPartDto(CarPart carPart) {
        return new CarPartDTO(
                carPart.getName(),
                carPart.getCategory().getName(),
                carPart.getPrice(),
                carPart.getDescription()
        );
    }

    private CarPart convertCarPartDtoToCarPart(CarPartDTO carPartDTO) {

        List<CarPartCategory> carPartCategoryList =
                carPartCategoryRepository.findByName(carPartDTO.getCategoryString());

        CarPartCategory carPartCategory = carPartCategoryList.getFirst();

        return new CarPart(
                carPartDTO.getName(),
                carPartCategory,
                carPartDTO.getPrice(),
                carPartDTO.getDescription()
        );
    }
}
