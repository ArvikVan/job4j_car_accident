package accident.repository;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.model.Rule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ArvikV
 * @version 1.0
 * @since 04.02.2022
 */
@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * метод получения всех аварий
     * @return на выходе аварии
     * стандартный запрос
     */
    public Collection<Accident> getAccidents() {
        return jdbc.query("select id, name_accident , text , address, type_id "
                + "from accident", (rs, row) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name_accident"));
            accident.setText(rs.getString("text"));
            accident.setAddress(rs.getString("address"));
            accident.setType(findTypeId(rs.getInt("type_id")));
            accident.setRules(findRulesByIdFromAccidentRule(accident.getId()));
            return accident;
        });
    }

    /**
     * метод получения всех типов
     * @return на выходе все типы
     * запрос стандартный
     */
    public Collection<AccidentType> getAccidentTypes() {
        return jdbc.query("select id, name from accident_type", (rs, row) -> {
           AccidentType accidentType = new AccidentType();
           accidentType.setId(rs.getInt("id"));
           accidentType.setName(rs.getString("name"));
           return accidentType;
        });
    }

    /**
     * меод получения статей
     * @return на выходе статьи
     */
    public Collection<Rule> getAccidentRules() {
        return jdbc.query("select * from rule", (rs, row) -> {
            Rule rule = new Rule();
            rule.setId(rs.getInt("id"));
            rule.setName(rs.getString("name"));
            return rule;
        });
    }

    /**
     * метод создания аварии
     * @param accident авария на входе
     * @param ids ид типа
     * @return на выходе созданная авария
     * сделано через зад, как обрабатывать с помощью мапы не додумался
     * использщуем апдейт стандартный запрос, скл сохраняем в препст, чтоб потом его обработать используя параметры
     */
    public Accident create(Accident accident, String[] ids) {
            KeyHolder key = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                        "insert into accident (name_accident, text, address, type_id) values (?, ?, ?, ?)",
                        new String[]{"id"});
                ps.setString(1, accident.getName());
                ps.setString(2, accident.getText());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            }, key);
            updateAccidentRule(accident, key.getKey().intValue());
        return accident;
    }

    /**
     * метод поиска по ид
     * @param id ид по которому надо искать
     * @return на выходе авария по ид
     * запрос можно член сломать, но стандартный
     */
    public Accident findById(int id) {
            try {
                return jdbc.queryForObject("select acc.id as a_id, acc.name_accident as a_name, acc.text as a_text"
                        + ", acc.address as a_address"
                        + ", acc.type_id as a_type_id, t.name as t_name from accident as acc "
                        + "left join accident_type t on acc.type_id = t.id "
                        + "where acc.id = ?", (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("a_id"));
                    accident.setName(rs.getString("a_name"));
                    accident.setText(rs.getString("a_text"));
                    accident.setAddress(rs.getString("a_address"));
                    accident.setType(AccidentType.of(rs.getInt("a_type_id"), rs.getString("t_name")));
                    return accident;
                }, id);
            } catch (EmptyResultDataAccessException e) {
                return null;
            }
    }

    /**
     * метод поиска типа по ид
     * @param id ид типа
     * @return на выходе нужный тип аварии
     * запрос простой
     */
    public AccidentType findTypeId(int id) {
        return jdbc.queryForObject("select id, name from accident_type where id = ?", (rs, row) -> {
            AccidentType accidentType = new AccidentType();
            accidentType.setId(rs.getInt("id"));
            accidentType.setName(rs.getString("name"));
            return accidentType;
        }, id);
    }

    /**
     * метод получения правил
     * @param ids на входе иды статей что выбрали
     * @return на выходе статьи что добавили
     * добавляем в сет швы стринговый преобразованный в инту
     */
    public Set<Rule> getRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        Stream.of(ids)
                .mapToInt(Integer::parseInt)
                .forEach(id -> rules.add(findRuleById(id)));
        return rules;
    }

    /**
     * метод поиска статьи по ид
     * @param id ид статьи
     * @return на выходе статья
     * все просто
     */
    public Rule findRuleById(int id) {
        return jdbc.queryForObject("select id, name from rule where id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }, id);
    }

    /**
     * метод поиска ид статьи по ид аварии в смежной таблице
     * @param id ид статьи
     * @return на выходе статьи в множестве
     */
    private Set<Rule> findRulesByIdFromAccidentRule(int id) {
        List<Integer> rules = jdbc.query(
                "select rule_id from accident_rule where accident_id = ?",
                (rs, row) -> rs.getInt("rule_id"), id);
        return rules.stream().map(this::findRuleById).collect(Collectors.toSet());
    }

    /**
     * метод обновления авария, вносим изменения тут
     * @param accident авария для изменения
     * @return авария после изменения
     * простой запрос, не забыли обновить и смежную таблу, предварительно удалив старую запись
     */
    public Accident update(Accident accident) {
        jdbc.update("update accident set name_accident = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("delete from accident_rule where accident_id = ?", accident.getId());
        updateAccidentRule(accident, accident.getId());
        return accident;
    }

    /**
     * метод обновления смежной таблицы
     * @param accident получаем с помоью этого поля статьи
     * @param accidentId ид аварии в табле
     */
    private void updateAccidentRule(Accident accident, int accidentId) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) values (?, ?)",
                    accidentId, rule.getId());
        }
    }

    /**
     * метод создания аварии с валидацией
     * @param accident авария
     * @param ids ид типа
     * @return на выходе либо заводим либо обновляем
     */
    public Accident save(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            return create(accident, ids);
        }
        return update(accident);
    }

}
