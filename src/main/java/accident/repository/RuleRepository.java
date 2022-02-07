package accident.repository;

import accident.model.Rule;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 08.02.2022
 */
public interface RuleRepository extends CrudRepository<Rule, Integer> {
}
