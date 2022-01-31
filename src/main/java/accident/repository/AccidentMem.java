package accident.repository;

import accident.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author ArvikV
 * @version 1.
 * @since 31.01.2022
 * AccidentMem - хранилище инцидентов.
 * добавлен метод update, который должен обновлять мапу по ключу
 */
@Repository
public class AccidentMem {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();
    private  int id = 4;

    public AccidentMem() {
        accidents.put(1, new Accident(1, "name1", "text1", "address1"));
        accidents.put(2, new Accident(2, "name2", "text2", "address2"));
        accidents.put(3, new Accident(3, "name3", "text3", "address3"));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    /**
     * метод создания новой аварии
     * @param accident сама авария со всеми параметрами
     *                 вставляем в мапу аварию с ид
     *                 при этом если ид 0 то увеличиваем его
     */
    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(id++);
        }
        accidents.put(id, accident);
    }

    /**
     * метод обновления
     * @param id ид по которому надо обновить
     * @param accident то что надо обновить
     */
    public void update(int id, Accident accident) {
        accidents.replace(id, accident);
    }
}
