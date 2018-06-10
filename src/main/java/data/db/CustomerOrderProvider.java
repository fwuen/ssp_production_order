package data.db;

import data.model.CustomerOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerOrderProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    private EntityManager em;

    /*
    public CustomerOrderProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }*/

    public void writeCustomerOrder(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public void removeCustomerOrder(CustomerOrder customerOrder) {
        if (!em.contains(customerOrder)) {
            customerOrder = em.merge(customerOrder);
        }
        em.remove(customerOrder);
    }

    public CustomerOrder findCustomerOrderById(int id) {
        return em.find(CustomerOrder.class, id);
    }
}
