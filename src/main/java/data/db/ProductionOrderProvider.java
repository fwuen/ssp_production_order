package data.db;

import data.model.ProductionOrder;
import data.model.ProductionOrderItems;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class ProductionOrderProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    EntityManager em;

    /*
    public ProductionOrderProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }*/

    public void writeProductionOrder(ProductionOrder productionOrder) {
        em.persist(productionOrder);
    }

    public ProductionOrder findProductionOrderById(int id) {
        return em.find(ProductionOrder.class, id);
    }

    public void removeProductionOrder(ProductionOrder productionOrder) {
        if (!em.contains(productionOrder)) {
            productionOrder = em.merge(productionOrder);
        }
        em.remove(productionOrder);
    }

    public List<ProductionOrder> findAllProductionOrders() {
        Query query = em.createQuery("SELECT e FROM ProductionOrder e");
        return (List<ProductionOrder>) query.getResultList();
    }

    public void writeProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.persist(productionOrderItems);
    }

    public void updateProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.merge(productionOrderItems);
    }

    public void removeProductionOrderItems(ProductionOrderItems productionOrderItems) {
        if (!em.contains(productionOrderItems)) {
            productionOrderItems = em.merge(productionOrderItems);
        }
        em.remove(productionOrderItems);
    }
}
