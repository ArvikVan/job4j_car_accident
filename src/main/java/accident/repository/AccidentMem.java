package accident.repository;

import accident.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author ArvikV
 * @version 1.0
 * @since 31.01.2022
 * AccidentMem - хранилище инцидентов.
 */
@Repository
public class AccidentMem {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        accidents.put(1, new Accident(1, "name1", "text1", "address1"));
        accidents.put(2, new Accident(2, "name2", "text2", "address2"));
        accidents.put(3, new Accident(3, "name3", "text3", "address3"));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }
}
