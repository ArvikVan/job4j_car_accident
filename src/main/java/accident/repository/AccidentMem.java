package accident.repository;

import accident.model.Accident;
import accident.model.AccidentType;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ArvikV
 * @version 1.3
 * @since 31.01.2022
 * AccidentMem - хранилище инцидентов.
 * добавлен метод update, который должен обновлять мапу по ключу
 * 1.2 добавлен атомикИнтежер как счетчик, модифицирован метод create(Accident accident)
 * добавлен метод findById для пользования в AccidentControl
 * 1.3 Во избежание NPE добавил к редактированию и редакт.типа аварии
 * добавлены методы findTypeId(int id) getAccidentTypes() и мапа accidentTypes
 */
@Repository
public class AccidentMem {
    private final HashMap<Integer, Accident> accidents = new HashMap<>();

    private final HashMap<Integer, AccidentType> accidentTypes = new HashMap<>();

    private final AtomicInteger countId = new AtomicInteger(3);

    public AccidentMem() {
        accidentTypes.put(1, AccidentType.of(1, "Two cars"));
        accidentTypes.put(2, AccidentType.of(2, "Human and vehicle"));
        accidentTypes.put(3, AccidentType.of(3, "Vehicle and bycicle"));
        accidents.put(1, new Accident(
                1,
                "ДТП",
                "Лобовое столкновение, тс восстановлению не подлежат",
                "Комсомольская/Пролетарская", accidentTypes.get(1)));
        accidents.put(2, new Accident(
                2,
                "Автомобиль разбился",
                "Водитель ТС не справился с управлением и врезался в столб",
                "Кирова/Партизанская", accidentTypes.get(2)));
        accidents.put(3, new Accident(
                3,
                "Порча имущества",
                "Гр.Залупин бросил кирпич в ветровое стекло и разбил его",
                "ПромЗона-246", accidentTypes.get(3)));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getAccidentTypes() {
        return accidentTypes.values();
    }

    /**
     * метод создания новой аварии
     * @param accident сама авария со всеми параметрами
     *                 вставляем в мапу аварию с ид
     *                если ключа нет в мапе то увеличиваем счетчик
     */
    public void create(Accident accident) {
        if (!accidents.containsKey(accident.getId())) {
            accident.setId(countId.incrementAndGet());
        }
        accidents.put(countId.addAndGet(0), accident);
    }

    /**
     * метод обновления
     * @param id ид по которому надо обновить
     * @param accident то что надо обновить
     */
    public void update(int id, Accident accident) {
        accidents.replace(id, accident);
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }

    public AccidentType findTypeId(int id) {
        return accidentTypes.get(id);
    }
}
