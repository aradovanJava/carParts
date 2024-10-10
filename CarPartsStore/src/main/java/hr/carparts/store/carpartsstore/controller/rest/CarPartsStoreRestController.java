package hr.carparts.store.carpartsstore.controller.rest;

import hr.carparts.store.carpartsstore.dto.CarPartDTO;
import hr.carparts.store.carpartsstore.service.CarPartsStoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
@AllArgsConstructor
public class CarPartsStoreRestController {

    private CarPartsStoreService carPartsStoreService;

    @GetMapping("/carParts")
    public List<CarPartDTO> getAllCarParts() {
        return carPartsStoreService.findAll();
    }

    @GetMapping("/carPart/{id}")
    public ResponseEntity<CarPartDTO> getCarPartById(@PathVariable Integer id) {
        Optional<CarPartDTO> carPartDTOOptional = carPartsStoreService.findById(id);

        return carPartDTOOptional.map(carPartDTO -> new ResponseEntity<>(carPartDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/carPart/name/{name}")
    public ResponseEntity<List<CarPartDTO>> getCarPartByName(@PathVariable String name) {
        return new ResponseEntity<>(carPartsStoreService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/carPart")
    public void saveNewCarPart(@RequestBody CarPartDTO carPartDTO) {
        carPartsStoreService.save(carPartDTO);
    }

}
