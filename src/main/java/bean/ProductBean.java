package bean;

import data.db.DatabaseManager;
import data.model.Product;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "productBean")
public class ProductBean {
    public List<Product> allProducts() {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProducts();
    }
}
