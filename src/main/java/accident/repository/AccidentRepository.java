package accident.repository;

import accident.model.Accident;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * @author ArvikV
 * @version 1.0
 * @since 08.02.2022
 * Добавил аннотацию с запросом во избежание LIA, как я понял открыли сессию
 */
public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    @Query("from Accident a join fetch a.rule")
    List<Accident> findAll();

    @Query(value = "from Accident a join fetch a.rule where a.id=?1")
    Accident findById(int id);
}
