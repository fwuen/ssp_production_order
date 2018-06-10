package bean;

import data.db.ProductProvider;
import data.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean {
    @EJB
    ProductProvider productProvider;

    @Getter
    @Setter
    List<Product> allProducts;

    @PostConstruct
    public void init() {
        allProducts = allProducts();
    }

    public List<Product> allProducts() {
        return productProvider.findAllProducts();
    }
}
