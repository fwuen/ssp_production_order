package data.db;

import data.model.Product;
import data.model.ProductType;
import data.model.Production;
import data.model.ProductionOrder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {
    private EntityManager em;
    
    public DatabaseManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderJPA");
        this.em = emf.createEntityManager();
    }
    
    public void writeProduct(Product product) {
        em.persist(product);
    }
    
    public void findProductById(int id) {
        em.find(Product.class, id);
    }
    
    public void removeProduct(Product product) {
        em.remove(product);
        em.flush();
    }
    
    public void writeProduction(Production production) {
        em.persist(production);
    }
    
    public void findProductionById(int id) {
        em.find(Production.class, id);
    }
    
    public void removeProduction(Production production) {
        em.remove(production);
        em.flush();
    }
    
    public void writeProductType(ProductType productType) {
        em.persist(productType);
    }
    
    public void findProductTypeById(int id) {
        em.find(ProductType.class, id);
    }
    
    public void removeProductType(ProductType productType) {
        em.remove(productType);
        em.flush();
    }
    
    public void writeProductionOrder(ProductionOrder productionOrder) {
        em.persist(productionOrder);
    }
    
    public void findProductionOrderById(int id) {
        em.find(ProductionOrder.class, id);
    }
    
    public void removeProductionOrder(ProductionOrder productionOrder) {
        em.remove(productionOrder);
        em.flush();
    }
}
