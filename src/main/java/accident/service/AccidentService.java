package accident.service;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.model.Rule;
import accident.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author ArvikV
 * @version 1.0
 * @since 31.01.2022
 * AccidentService - описывающий логику работы. Отмечено аннотацией @Service.
 */
@Service
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    public AccidentService(AccidentRepository accidentRepository, AccidentTypeRepository accidentTypeRepository,
                           RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
    }

    public Collection<Accident> getAccident() {
        List<Accident> accidentList = new ArrayList<>();
        accidentRepository.findAll().forEach(accidentList::add);
        return accidentList;
    }

    public Collection<AccidentType> getAccidentTypes() {
        List<AccidentType> accidentTypeList = new ArrayList<>();
        accidentTypeRepository.findAll().forEach(accidentTypeList::add);
        return accidentTypeList;
    }

    public Collection<Rule> getAccidentRules() {
        List<Rule> ruleList = new ArrayList<>();
        ruleRepository.findAll().forEach(ruleList::add);
        return ruleList;
    }

    public Accident findById(int id) {
        return accidentRepository.findById(id);
    }

    public AccidentType findTypeId(int id) {
        return accidentTypeRepository.findById(id).get();
    }

    public Set<Rule> getRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        Stream.of(ids).mapToInt(Integer::parseInt).forEach(id -> rules.add(ruleRepository.findById(id).get()));
        return rules;
    }

    public void create(Accident accident, String[] ids) {
            for (String id : ids) {
                accident.addRule(ruleRepository.findById(Integer.parseInt(id)).get());
            }
            accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
            accidentRepository.save(accident);
    }

}
