package data.db;

import data.model.ProductType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ProductTypeProvider {
    EntityManager em;

    public ProductTypeProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnit");
        this.em = emf.createEntityManager();
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
        em.getTransaction().commit();
    }

    public List<ProductType> findAllProductTypes() {
        Query query = em.createQuery("SELECT e FROM ProductType e");
        return (List<ProductType>) query.getResultList();
    }
}
