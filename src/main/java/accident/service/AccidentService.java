package accident.service;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.model.Rule;
import accident.repository.AccidentJdbcTemplate;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ArvikV
 * @version 1.0
 * @since 31.01.2022
 * AccidentService - описывающий логику работы. Отмечено аннотацией @Service.
 */
@Service
public class AccidentService {

    private final AccidentJdbcTemplate accidentJdbcTemplate;

    public AccidentService(AccidentJdbcTemplate accidentJdbcTemplate) {
        this.accidentJdbcTemplate = accidentJdbcTemplate;
    }

    public Collection<Accident> getAccident() {
        return accidentJdbcTemplate.getAccidents();
    }

    public Collection<AccidentType> getAccidentTypes() {
        return accidentJdbcTemplate.getAccidentTypes();
    }

    public Collection<Rule> getAccidentRules() {
        return accidentJdbcTemplate.getAccidentRules();
    }

    public Accident findById(int id) {
        return accidentJdbcTemplate.findById(id);
    }

    public AccidentType findTypeId(int id) {
        return accidentJdbcTemplate.findTypeId(id);
    }

    public Set<Rule> getRules(String[] ids) {
        return accidentJdbcTemplate.getRules(ids);
    }

    public void create(Accident accident, String[] id) {
        accidentJdbcTemplate.save(accident, id);
    }

}
