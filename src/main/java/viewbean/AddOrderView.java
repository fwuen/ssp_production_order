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
    List<String> selectedProductNames = new ArrayList();

    @Getter
    @Setter
    int customerId;

    public void submitForm() {
        /*
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customerId);
        List<CustomerOrder> customerOrders = new ArrayList<>();

        databaseManager.writeCustomerOrder(customerOrder);

        customerOrders.add(customerOrder);

        //List<ProductionOrder> productionOrders = new ArrayList<>();
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setCustomerOrders(customerOrders);
        //productionOrder.setProductionOrderItems(selectedProducts);
        //productionOrder.setCustomerOrders(customerOrders);
        //productionOrders.add(productionOrder);
        //customerOrder.setProductionOrders(productionOrders);

        databaseManager.writeProductionOrder(productionOrder);*/
        ProductionOrder productionOrder = new ProductionOrder();
        List<Product> selectedProducts = new ArrayList<>();
        for (String productName : selectedProductNames) {
            for (Product product : allProducts) {
                if (product.getpName().equals(productName)) {
                    selectedProducts.add(product);
                }
            }
        }
        productionOrder.setProductionOrderItems(selectedProducts);

        databaseManager.writeProductionOrder(productionOrder);

        List<ProductionOrder> productionOrders = new ArrayList<>();
        productionOrders.add(productionOrder);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customerId);
        customerOrder.setProductionOrders(productionOrders);

        databaseManager.writeCustomerOrder(customerOrder);
    }

    public List<Product> allProducts() {
        databaseManager = new DatabaseManager();
        return databaseManager.findAllProducts();
    }
}
