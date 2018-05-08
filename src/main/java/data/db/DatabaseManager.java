package data.db;

import data.model.Product;
import data.model.ProductType;
import data.model.Production;
import data.model.ProductionOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DatabaseManager {
    private EntityManager em;
    
    public DatabaseManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnit");
        this.em = emf.createEntityManager();
    }
    
    public void writeProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT e FROM Product e");
        return (List<Product>) query.getResultList();
    }
    
    public void removeProduct(Product product) {
        em.remove(product);
        em.flush();
    }
    
    public void writeProduction(Production production) {
        em.getTransaction().begin();
        em.persist(production);
        em.getTransaction().commit();
    }
    
    public Production findProductionById(int id) {
        return em.find(Production.class, id);
    }
    
    public void removeProduction(Production production) {
        em.remove(production);
        em.flush();
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
        em.remove(productType);
        em.flush();
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
        em.remove(productionOrder);
        em.flush();
    }

    public List<ProductionOrder> findAllProductionOrders() {
        Query query = em.createQuery("SELECT e FROM ProductionOrder e");
        return (List<ProductionOrder>) query.getResultList();
    }
}
