package logic;

import data.db.DatabaseManager;
import data.model.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//TODO mehrere Orders geht noch nicht
public class ProductionOrderManager {
    DatabaseManager databaseManager = new DatabaseManager();
    List<ProductType> productTypes = databaseManager.findAllProductTypes();

    public void createProductionOrdersFromCustomerOrders(List<Order> orders) {
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
            for (Order order : orders) {
                boolean customerOrderAdded = false;

                for (Product product : order.getProducts()) {
                    if (product.getProductTypeByProductId().getPtId() == productType.getPtId()) {
                        if(productTypesTargetDate.getTargetDate() == null || order.getTargetDate().before(productTypesTargetDate.getTargetDate())) {
                            productTypesTargetDate.setTargetDate(order.getTargetDate());
                        }
                        productTypesTargetDate.setProductType(productType);
                        productTypesTargetDate.getProducts().add(product);

                        if(!customerOrderAdded) {
                            customerOrderAdded = true;
                            CustomerOrder customerOrder = new CustomerOrder();
                            customerOrder.setTargetDate(new java.sql.Date(order.getTargetDate().getTime()));
                            customerOrder.setCustomerId(order.getCustomerId());

                            List<CustomerOrder> customerOrders = new ArrayList<>();
                            customerOrders.add(customerOrder);
                            productTypesTargetDate.setCustomerOrders(customerOrders);
                        }
                    }
                }
            }
            productTypesTargetDates.add(productTypesTargetDate);
        }

        for (ProductTypesTargetDate productTypesTargetDate : productTypesTargetDates) {
            if(productTypesTargetDate.getProducts().size() > 0) {
                List<ProductionOrder> productionOrders = new ArrayList<>();
                ProductionOrder productionOrder = new ProductionOrder();
                productionOrder.setProductionOrderItems(productTypesTargetDate.getProducts());
                databaseManager.writeProductionOrder(productionOrder);

                productionOrders.add(productionOrder);

                for (CustomerOrder customerOrder : productTypesTargetDate.getCustomerOrders()) {
                    customerOrder.setProductionOrders(productionOrders);
                    databaseManager.writeCustomerOrder(customerOrder);
                }

                for (Product product : productionOrder.getProductionOrderItems()) {
                    Production production = new Production();
                    production.setPrTimestamp(getPrTimestampForTargetDate(productTypesTargetDate.getTargetDate()));
                    production.setProductionOrderByProductionOrderId(productionOrder);
                    production.setMachineId(product.getpId() / 10 + 1);
                    production.setToolId(new Random().nextInt(100) + 1);
                    production.setProductByProductId(product);

                    databaseManager.writeProduction(production);
                }
            }
        }

    }

    private Timestamp getPrTimestampForTargetDate(Date targetDate) {
        Date date = new Date(targetDate.getTime() + new Random().nextInt(86400000));

        return new Timestamp(date.getTime());
    }

}
