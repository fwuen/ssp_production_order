package data.db;

import data.model.Production;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Stateless
public class ProductionProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    EntityManager em;

    /*
    public ProductionProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }*/

    public void writeProduction(Production production) {
        em.persist(production);
    }

    public void updateProduction(Production production) {
        em.merge(production);
    }

    public Production findProductionById(int id) {
        return em.find(Production.class, id);
    }

    public void removeProduction(Production production) {
        if (!em.contains(production)) {
            production = em.merge(production);
        }
        em.remove(production);
    }

    public List<Production> findAllProductions() {
        Query query = em.createQuery("SELECT e FROM Production e");
        return (List<Production>) query.getResultList();
    }

    public List<Production> findProductionsByProductionDate() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Production> q = cb.createQuery(Production.class);
        Root<Production> from = q.from(Production.class);

        Predicate startDatePredicate = cb.greaterThanOrEqualTo(from.get("prTimestamp"), new Timestamp(System.currentTimeMillis()));

        q.select(from).where(startDatePredicate);
        q.orderBy(cb.asc(from.get("prTimestamp")));
        Query query = em.createQuery(q);
        List<Production> productions = query.getResultList();
        return productions;
    }
}
