package data.db;

import data.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Deprecated
public class DatabaseManager {
    private EntityManager em;
    
    public void writeProduct(Product product) {
        em.persist(product);

    }

    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);

    }

    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT e FROM Product e");
        return (List<Product>) query.getResultList();
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
    
    public void writeProductType(ProductType productType) {
        em.getTransaction().begin();
        em.persist(productType);
        em.getTransaction().commit();
    }
    
    public ProductType findProductTypeById(int id) {
        return em.find(ProductType.class, id);
    }
    
    public void removeProductType(ProductType productType) {
        em.getTransaction().begin();
        if (!em.contains(productType)) {
            productType = em.merge(productType);
        }
        em.remove(productType);
        em.getTransaction().commit();
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
        em.remove(productionOrder);
        em.getTransaction().commit();
    }

    public List<ProductionOrder> findAllProductionOrders() {
        Query query = em.createQuery("SELECT e FROM ProductionOrder e");
        return (List<ProductionOrder>) query.getResultList();
    }

    public void writeCustomerOrder(CustomerOrder customerOrder) {
        em.getTransaction().begin();
        em.persist(customerOrder);
        em.getTransaction().commit();
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

    public CustomerOrder findCustomerOrderById(int id) {
        return em.find(CustomerOrder.class, id);
    }

    public List<ProductType> findAllProductTypes() {
        Query query = em.createQuery("SELECT e FROM ProductType e");
        return (List<ProductType>) query.getResultList();
    }

    public void removeProductionOrderItems(ProductionOrderItems productionOrderItems) {
        em.getTransaction().begin();
        if (!em.contains(productionOrderItems)) {
            productionOrderItems = em.merge(productionOrderItems);
        }
        em.remove(productionOrderItems);
        em.getTransaction().commit();
    }
}
