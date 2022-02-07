package accident.repository;

import accident.model.AccidentType;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 08.02.2022
 */
public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
}
