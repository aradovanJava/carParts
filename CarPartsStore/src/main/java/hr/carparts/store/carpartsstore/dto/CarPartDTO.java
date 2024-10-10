package hr.carparts.store.carpartsstore.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPartDTO {

    @Size(min = 3, max = 50, message = "The cart part name must contain between {min} and {max} characters!")
    private String name;

    @NotEmpty(message = "Category name is required!")
    private String categoryString;

    @NotNull(message = "The car part price cannot be empty!")
    @DecimalMin(value = "0.0", message = "The cart part price cannot be negative!")
    private BigDecimal price;

    private String description;
}
