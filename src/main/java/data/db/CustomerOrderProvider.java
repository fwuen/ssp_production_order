package data.db;

import data.model.CustomerOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerOrderProvider {
    private EntityManager em;

    public CustomerOrderProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnit");
        this.em = emf.createEntityManager();
    }
    public void writeCustomerOrder(CustomerOrder customerOrder) {
        em.getTransaction().begin();
        em.persist(customerOrder);
        em.getTransaction().commit();
    }

    public void removeCustomerOrder(CustomerOrder customerOrder) {
        em.getTransaction().begin();
        if (!em.contains(customerOrder)) {
            customerOrder = em.merge(customerOrder);
        }
        em.remove(customerOrder);
        em.getTransaction().commit();
    }

    public CustomerOrder findCustomerOrderById(int id) {
        return em.find(CustomerOrder.class, id);
    }
}
