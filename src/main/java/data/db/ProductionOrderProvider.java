package data.db;

import data.model.ProductionOrder;
import data.model.ProductionOrderItems;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ProductionOrderProvider {
    EntityManager em;

    public ProductionOrderProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnit");
        this.em = emf.createEntityManager();
    }
    public void writeProductionOrder(ProductionOrder productionOrder) {
        em.getTransaction().begin();
        em.persist(productionOrder);
        em.getTransaction().commit();
    }

    public ProductionOrder findProductionOrderById(int id) {
        return em.find(ProductionOrder.class, id);
    }

    public void removeProductionOrder(ProductionOrder productionOrder) {
        em.getTransaction().begin();
        if (!em.contains(productionOrder)) {
            productionOrder = em.merge(productionOrder);
        }
        em.getTransaction().commit();
    }

    public List<ProductionOrder> findAllProductionOrders() {
        Query query = em.createQuery("SELECT e FROM ProductionOrder e");
        return (List<ProductionOrder>) query.getResultList();
    }

    public void writeProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.getTransaction().begin();
        em.persist(productionOrderItems);
        em.getTransaction().commit();
    }

    public void updateProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.getTransaction().begin();
        em.merge(productionOrderItems);
        em.getTransaction().commit();
    }

    public void removeProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.getTransaction().begin();
        if (!em.contains(productionOrderItems)) {
            productionOrderItems = em.merge(productionOrderItems);
        }
        em.getTransaction().commit();
    }
}
