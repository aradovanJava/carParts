package hr.carparts.store.carpartsstore.repository;

import hr.carparts.store.carpartsstore.model.nosql.NoSqlCarPart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDbCarPartRepository extends MongoRepository<NoSqlCarPart, String> {
    List<NoSqlCarPart> findByNameIgnoreCaseLike(String name);
}
