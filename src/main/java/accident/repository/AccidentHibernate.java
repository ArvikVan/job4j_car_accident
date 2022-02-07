package accident.repository;

import accident.model.Accident;
import accident.model.AccidentType;
import accident.model.Rule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * @author ArvikV
 * @version 1.0
 * @since 07.02.2022
 */
@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = sf.openSession();
        try {
            T rsl = command.apply(session);
            session.beginTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Collection<Accident> getAccidents() {
       return this.tx(session -> session.createQuery("from Accident order by id").list());
    }

    public Collection<AccidentType> getAccidentTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class).list();
        }
    }

    public Collection<Rule> getAccidentRules() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class).list();
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident where id = :id", Accident.class)
                    .setParameter("id", id).uniqueResult();
        }
    }

    public Accident save(Accident accident, String[] ids) {
        tx(session -> {
            for (String id : ids) {
                accident.addRule(session.get(Rule.class, Integer.parseInt(id)));
            }
            session.saveOrUpdate(accident);
            return accident;
        });
        return null;
    }

    public AccidentType findTypeId(int id) {
        return (AccidentType) tx(session -> session.createQuery("from AccidentType at where at.id = :atId")
                .setParameter("atId", id).uniqueResult());
    }

    public Set<Rule> getRules(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        Stream.of(ids)
                .mapToInt(Integer::parseInt)
                .forEach(id -> rules.add(findRuleById(id)));
        return rules;
    }

    public Rule findRuleById(int id) {
        return (Rule) tx(session -> session.createQuery("from Rule r where r.id = :rId")
                .setParameter("rId", id).uniqueResult());
    }

}
