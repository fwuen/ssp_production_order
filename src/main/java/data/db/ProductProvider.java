package data.db;

import data.model.Product;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class ProductProvider {
    @PersistenceContext(unitName = "ProductionOrderPersistenceUnit")
    private EntityManager em;

    /*
    public ProductProvider() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProductionOrderPersistenceUnitManual");
        em = emf.createEntityManager();
    }*/

    public void writeProduct(Product product) {
        em.persist(product);
    }

    public void updateProduct(Product product) {
        em.merge(product);
    }

    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT e FROM Product e");
        return (List<Product>) query.getResultList();
    }
}
