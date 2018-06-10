package bean;

import data.db.ProductProvider;
import data.model.*;
import ejb.ProductionOrderLocal;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "addOrderViewBean")
@SessionScoped
public class AddOrderView {
    @EJB
    ProductProvider productProvider;

    @Getter
    @Setter
    List<Product> allProducts;

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

    @EJB
    ProductionOrderLocal productionOrder;

    ResourceBundle msgs = ResourceBundle.getBundle("internationalization.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init() {
        allProducts = allProducts();
    }

    public void submitForm() {
        Order newOrder = new Order();
        newOrder.setCustomerId(customerId);
        List<Product> selectedProducts = new ArrayList<>();
        for (String productName : selectedProductNames) {
            for (Product product : allProducts) {
                if (product.getpName().equals(productName)) {
                    selectedProducts.add(product);
                    break;
                }
            }
        }
        newOrder.setProducts(selectedProducts);
        newOrder.setTargetDate(targetDate);

        orders.add(newOrder);

        customerId = 0;
        targetDate = null;
        selectedProductNames = new ArrayList<>();

        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, msgs.getString("Added"), msgs.getString("OrderAdded"));

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void createProductionOrders() {
        productionOrder.writeProductionOrders(orders);
        orders.clear();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, msgs.getString("Created"), msgs.getString("ProductionOrderCreated"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        List<ProductionOrderItems> productionOrderItemsList = new ArrayList<>();
        List<Product> selectedProducts = new ArrayList<>();

        for (String productName : selectedProductNames) {
            for (Product product : allProducts) {
                if (product.getpName().equals(productName)) {
                    selectedProducts.add(product);
                }
            }
        }

        for (Product product : selectedProducts) {
            ProductionOrderItems productionOrderItems = new ProductionOrderItems();
            productionOrderItems.setProductionOrderByPoId(productionOrder);
            productionOrderItems.setProductByPId(product);
            productionOrderItems.setCnt(1);
        }

        productionOrder.setProductionOrderItems(productionOrderItemsList);
        return productionOrder;
    }

    public List<Product> allProducts() {
        return productProvider.findAllProducts();
    }
}
