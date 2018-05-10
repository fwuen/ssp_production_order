package viewbean;

import data.db.DatabaseManager;
import data.model.CustomerOrder;
import data.model.Product;
import data.model.ProductionOrder;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "addOrderViewBean")
@ViewScoped
public class AddOrderView {
    DatabaseManager databaseManager = new DatabaseManager();

    @Getter
    @Setter
    List<Product> allProducts = allProducts();

    @Getter
    @Setter
    List<Product> selectedProducts = new ArrayList();

    @Getter
    @Setter
    int customerId;

    public void submitForm() {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customerId);
        List<ProductionOrder> productionOrders = new ArrayList<>();
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProductionOrderItems(selectedProducts);
        productionOrders.add(productionOrder);
        customerOrder.setProductionOrders(productionOrders);

        databaseManager.writeCustomerOrder(customerOrder);
    }

    public List<Product> allProducts() {
        databaseManager = new DatabaseManager();
        return databaseManager.findAllProducts();
    }
}
