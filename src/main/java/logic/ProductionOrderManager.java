package logic;

import data.db.DatabaseManager;
import data.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//TODO
public class ProductionOrderManager {
    DatabaseManager databaseManager = new DatabaseManager();
    List<ProductType> productTypes = databaseManager.findAllProductTypes();

    private void createProductionOrdersFromCustomerOrders(List<Order> orders) {
        /*
        List<ProductTypesTargetDate> productTypesTargetDates = new ArrayList<>();
        boolean productFound = false;
        ProductTypesTargetDate productTypesTargetDateToWorkWith = null;

        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                for (ProductTypesTargetDate productTypesTargetDate : productTypesTargetDates) {
                    if (productTypesTargetDate.getProductId() == product.getpId()) {
                        productFound = true;
                        productTypesTargetDateToWorkWith = productTypesTargetDate;
                        break;
                    }
                }
                if(!productFound) {
                    productTypesTargetDateToWorkWith = new ProductTypesTargetDate();
                }
                productTypesTargetDateToWorkWith.setNumberOfOrderedProducts(productTypesTargetDateToWorkWith.getNumberOfOrderedProducts() + 1);
                productTypesTargetDateToWorkWith.setProductId(product.getpId());
            }
        }*/

        List<ProductTypesTargetDate> productTypesTargetDates = new ArrayList<>();

        for (ProductType productType : productTypes) {
            ProductTypesTargetDate productTypesTargetDate = new ProductTypesTargetDate();
            productTypesTargetDate.setProductType(productType);

            for (Order order : orders) {
                boolean customerOrderAdded = false;

                if(productTypesTargetDate.getTargetDate() == null || order.getTargetDate().before(productTypesTargetDate.getTargetDate())) {
                    productTypesTargetDate.setTargetDate(order.getTargetDate());
                }
                for (Product product : order.getProducts()) {
                    if (product.getProductTypeByProductId().getPtId() == productType.getPtId()) {
                        productTypesTargetDate.getProducts().add(product);

                        if(!customerOrderAdded) {
                            customerOrderAdded = true;
                            CustomerOrder customerOrder = new CustomerOrder();
                            customerOrder.setTargetDate(new java.sql.Date(order.getTargetDate().getTime()));
                            customerOrder.setCustomerId(order.getCustomerId());
                        }
                    }
                }
            }
            productTypesTargetDates.add(productTypesTargetDate);
        }

        for (ProductTypesTargetDate productTypesTargetDate : productTypesTargetDates) {
            ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setProductionOrderItems(productTypesTargetDate.getProducts());
            databaseManager.writeProductionOrder(productionOrder);

            for (CustomerOrder customerOrder : productTypesTargetDate.getCustomerOrders()) {
                List<ProductionOrder> productionOrders = new ArrayList<>();
                customerOrder.setProductionOrders(productionOrders);
                databaseManager.writeCustomerOrder(customerOrder);
            }
        }

        /**Hier jetzt Produktion erstellen, speichern**/

    }

}
