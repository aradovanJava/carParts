package hr.carparts.store.carpartsstore.model.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("carParts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoSqlCarPart {

    @Id
    private String id;
    private String name;
    private String category;
    private BigDecimal price;
    private String description;
}
