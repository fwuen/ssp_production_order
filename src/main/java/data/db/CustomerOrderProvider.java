package data.db;

import data.model.CustomerOrder;
import data.model.ProductionOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class CustomerOrderProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    private EntityManager em;

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

    public List<CustomerOrder> findAllCustomerOrders() {
        Query query = em.createQuery("SELECT e FROM CustomerOrder e");
        return (List<CustomerOrder>) query.getResultList();
    }
}
