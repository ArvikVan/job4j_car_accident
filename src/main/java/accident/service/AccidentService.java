package accident.service;

import accident.model.Accident;
import accident.repository.AccidentMem;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author ArvikV
 * @version 1.0
 * @since 31.01.2022
 * AccidentService - описывающий логику работы. Отмечено аннотацией @Service.
 */
@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Collection<Accident> getAccident() {
        return accidentMem.getAccidents();
    }
}
