package data.db;

import data.model.ProductType;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class ProductTypeProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    EntityManager em;

    /*
    public ProductTypeProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }*/

    public void writeProductType(ProductType productType) {
        em.persist(productType);
    }

    public ProductType findProductTypeById(int id) {
        return em.find(ProductType.class, id);
    }

    public void removeProductType(ProductType productType) {
        if (!em.contains(productType)) {
            productType = em.merge(productType);
        }
        em.remove(productType);
    }

    public List<ProductType> findAllProductTypes() {
        Query query = em.createQuery("SELECT e FROM ProductType e");
        return (List<ProductType>) query.getResultList();
    }
}
