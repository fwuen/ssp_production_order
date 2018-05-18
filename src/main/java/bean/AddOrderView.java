package bean;

import data.db.DatabaseManager;
import data.model.CustomerOrder;
import data.model.Order;
import data.model.Product;
import data.model.ProductionOrder;
import logic.ProductionOrderManager;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "addOrderViewBean")
@SessionScoped
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

    @Getter
    @Setter
    Date targetDate;

    @Getter
    @Setter
    Date tomorrow = new Date(System.currentTimeMillis() + 86400000);

    @Getter
    @Setter
    List<Order> orders = new ArrayList<>();

    //TODO
    public void submitForm() {
        /*
        ProductionOrder productionOrder = createProductionOrder();
        databaseManager.writeProductionOrder(productionOrder);

        CustomerOrder customerOrder = createCustomerOrder(productionOrder);
        databaseManager.writeCustomerOrder(customerOrder);*/
        Order newOrder = new Order();
        newOrder.setCustomerId(customerId);
        List<Product> selectedProducts = new ArrayList<>();
        for (String productName : selectedProductNames) {
            for (Product product : allProducts) {
                if (product.getpName().equals(productName)) {
                    selectedProducts.add(product);
                }
            }
        }
        newOrder.setProducts(selectedProducts);
        newOrder.setTargetDate(targetDate);

        orders.add(newOrder);

        customerId = 0;
        targetDate = null;
        selectedProductNames = new ArrayList<>();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("add.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createProductionOrders() {
        ProductionOrderManager productionOrderManager = new ProductionOrderManager();
        productionOrderManager.createProductionOrdersFromCustomerOrders(orders);
        orders.clear();
    }

    private CustomerOrder createCustomerOrder(ProductionOrder productionOrder) {
        List<ProductionOrder> productionOrders = new ArrayList<>();
        productionOrders.add(productionOrder);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customerId);
        customerOrder.setProductionOrders(productionOrders);
        customerOrder.setTargetDate(new java.sql.Date(targetDate.getTime()));
        return customerOrder;
    }

    private ProductionOrder createProductionOrder() {
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
        return productionOrder;
    }

    public List<Product> allProducts() {
        databaseManager = new DatabaseManager();
        return databaseManager.findAllProducts();
    }
}
