package hr.carparts.store.carpartsstore.controller.mvc;

import hr.carparts.store.carpartsstore.dto.CarPartDTO;
import hr.carparts.store.carpartsstore.model.CarPart;
import hr.carparts.store.carpartsstore.model.CarPartCategoryEnum;
import hr.carparts.store.carpartsstore.model.nosql.NoSqlCarPart;
import hr.carparts.store.carpartsstore.repository.MongoDbCarPartRepository;
import hr.carparts.store.carpartsstore.service.CarPartValidationServiceImpl;
import hr.carparts.store.carpartsstore.service.CarPartsStoreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc")
@AllArgsConstructor
@SessionAttributes({"carParts", "carPartCategoryList"})
public class CarPartsStoreController {

    private CarPartsStoreService carPartsStoreService;
    private CarPartValidationServiceImpl carPartValidationService;

    @GetMapping("/carPartsStore")
    public String getCarParts(Model model) {
        model.addAttribute("carPartCategoryList", CarPartCategoryEnum.values());
        model.addAttribute("carPartDTO", new CarPartDTO());
        return "carPartsStore";
    }

    @PostMapping("/carPartsStore")
    public String saveNewCarPart(@ModelAttribute @Valid CarPartDTO carPartDTO,
                                 BindingResult result,
                                 Model model)
    {
        if(!carPartDTO.getCategoryString().isEmpty()
                && !carPartValidationService.validateCarPartDtoModel(carPartDTO))
        {
            ObjectError error = new ObjectError("carPartDuplicationError",
                    "You provided a duplicate car part data!");
            result.addError(error);
        }

        if(result.hasErrors()) {
            return "carPartsStore";
        }

        carPartsStoreService.save(carPartDTO);

        List<CarPartDTO> carParts = (List<CarPartDTO>) model.getAttribute("carParts");

        if(carParts == null) {
            carParts = carPartsStoreService.findAll();
        }

        carParts.add(carPartDTO);

        return "redirect:carPartsStore";
    }

}
