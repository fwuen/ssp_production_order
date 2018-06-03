package data.db;

import data.model.Production;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ProductionProvider {
    EntityManager em;

    public ProductionProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnit");
        this.em = emf.createEntityManager();
    }

    public void writeProduction(Production production) {
        em.getTransaction().begin();
        em.persist(production);
        em.getTransaction().commit();
    }

    public void updateProduction(Production production) {
        em.getTransaction().begin();
        em.merge(production);
        em.getTransaction().commit();
    }

    public Production findProductionById(int id) {
        return em.find(Production.class, id);
    }

    public void removeProduction(Production production) {
        em.getTransaction().begin();
        if (!em.contains(production)) {
            production = em.merge(production);
        }
        em.remove(production);
        em.getTransaction().commit();
    }

    public List<Production> findAllProductions() {
        Query query = em.createQuery("SELECT e FROM Production e");
        return (List<Production>) query.getResultList();
    }

    public List<Production> findProductionsByProductionDate() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //Expression<Timestamp> ts = cb.currentTimestamp();
        //ParameterExpression<Date> parameter = cb.parameter(java.util.Date.class);

        CriteriaQuery<Production> q = cb.createQuery(Production.class);
        Root<Production> from = q.from(Production.class);

        Predicate startDatePredicate = cb.greaterThanOrEqualTo(from.<Date>get("prTimestamp"), new Timestamp(System.currentTimeMillis()));

        q.select(from).where(startDatePredicate);
        q.orderBy(cb.asc(from.get("prTimestamp")));
        return em.createQuery(q).getResultList();
    }
}
