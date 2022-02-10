package accident.repository;

import accident.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ArvikV
 * @version 1.0
 * @since 09.02.2022
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String name);
}
