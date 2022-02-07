package accident.repository;

import accident.model.Accident;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 08.02.2022
 */
public interface AccidentRepository extends CrudRepository<Accident, Integer> {
}
