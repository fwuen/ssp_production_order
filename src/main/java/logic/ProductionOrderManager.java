package logic;

import data.db.DatabaseManager;
import data.model.*;

import java.sql.Timestamp;
import java.util.*;

//TODO mehrere Orders geht noch nicht
public class ProductionOrderManager {
    DatabaseManager databaseManager = new DatabaseManager();
    List<ProductType> productTypes = databaseManager.findAllProductTypes();

    //todo: das hier ausreichend testen!
    public void createProductionOrdersFromCustomerOrders(List<Order> orders) {
        Map<ProductType, ProductTypesTargetDate> productTypesTargetDates = new HashMap<>();
        List<CustomerOrder> customerOrders = new ArrayList<>();

        for (Order order : orders) {
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrders.add(customerOrder);
            customerOrder.setTargetDate(new java.sql.Date(order.getTargetDate().getTime()));
            customerOrder.setCustomerId(order.getCustomerId());


            for (Product product : order.getProducts()) {
                //Schauen, ob ein ProductTypesTargetDate schon erstellt ist, ansonsten erstellen
                //Produkt zur Produktliste in ProductTypesTargetDate hinzufügen
                //Produkttyp in ProductTypesTargetDate setzen
                //targetDate in ProductTypesTargetDate setzen, falls Zieldatum eher oder null
                //customerOrder zu Liste in ProductTypesTargetDate hinzufügen
                //ProductTypesTargetDate zu Liste hinzufügen

                if (productTypesTargetDates.containsKey(product.getProductTypeByProductId())) {
                    ProductTypesTargetDate productTypesTargetDate = productTypesTargetDates.get(product.getProductTypeByProductId());
                    productTypesTargetDate.getProducts().add(product);
                    productTypesTargetDate.getCustomerOrders().add(customerOrder);
                    if (productTypesTargetDate.getTargetDate() == null || productTypesTargetDate.getTargetDate().after(customerOrder.getTargetDate())) {
                        productTypesTargetDate.setTargetDate(customerOrder.getTargetDate());
                    }
                } else {
                    ProductTypesTargetDate productTypesTargetDate = new ProductTypesTargetDate();
                    productTypesTargetDate.setTargetDate(customerOrder.getTargetDate());
                    productTypesTargetDate.getProducts().add(product);
                    productTypesTargetDate.getCustomerOrders().add(customerOrder);
                    productTypesTargetDate.setProductType(product.getProductTypeByProductId());
                    productTypesTargetDates.put(productTypesTargetDate.getProductType(), productTypesTargetDate);
                }

                /*
                ProductTypesTargetDate productTypesTargetDate = new ProductTypesTargetDate();

                if (productTypesTargetDate.getTargetDate() == null || productTypesTargetDate.getTargetDate().after(order.getTargetDate())) {
                    productTypesTargetDate.setTargetDate(order.getTargetDate());
                }
                productTypesTargetDate.setProductType(databaseManager.findProductTypeById(product.getProductTypeByProductId().getPtId()));
                productTypesTargetDate.getProducts().add(product);
                productTypesTargetDate.setCustomerOrders(customerOrders);*/
            }

            //productTypesTargetDates.add(productTypesTargetDate);
        }

        /*
        for (ProductType productType : productTypes) {
            ProductTypesTargetDate productTypesTargetDate = new ProductTypesTargetDate();

            for (Order order : orders) {
                boolean customerOrderAdded = false;

                for (Product product : order.getProducts()) {
                    if (product.getProductTypeByProductId().getPtId() == productType.getPtId()) {
                        if (productTypesTargetDate.getTargetDate() == null || order.getTargetDate().before(productTypesTargetDate.getTargetDate())) {
                            productTypesTargetDate.setTargetDate(order.getTargetDate());
                        }
                        productTypesTargetDate.setProductType(productType);
                        productTypesTargetDate.getProducts().add(product);

                        if (!customerOrderAdded) {
                            customerOrderAdded = true;
                            CustomerOrder customerOrder = new CustomerOrder();
                            customerOrder.setTargetDate(new java.sql.Date(order.getTargetDate().getTime()));
                            customerOrder.setCustomerId(order.getCustomerId());

                            customerOrders.add(customerOrder);
                            productTypesTargetDate.setCustomerOrders(customerOrders);
                        }
                    }
                }
                productTypesTargetDates.add(productTypesTargetDate);
            }
        }*/


        List<Product> alreadyAddedProducts = new ArrayList<>();

        for (Map.Entry<ProductType, ProductTypesTargetDate> productTypesTargetDate : productTypesTargetDates.entrySet()) {
        //for (ProductTypesTargetDate productTypesTargetDate : productTypesTargetDates) {
            if (productTypesTargetDate.getValue().getProducts().size() > 0) {
                List<ProductionOrder> productionOrders = new ArrayList<>();
                ProductionOrder productionOrder = new ProductionOrder();
                List<ProductionOrderItems> productionOrderItemsList = new ArrayList<>();

                for (Product product : productTypesTargetDate.getValue().getProducts()) {
                    if (alreadyAddedProducts.contains(product)) {
                        for (ProductionOrderItems productionOrderItems : productionOrderItemsList) {
                            if (productionOrderItems.getProductByPId().getpId() == product.getpId()) {
                                productionOrderItems.setCnt(productionOrderItems.getCnt() + 1);
                            }
                        }
                    } else {
                        ProductionOrderItems productionOrderItems = new ProductionOrderItems();
                        productionOrderItems.setCnt(1);
                        productionOrderItems.setProductByPId(product);
                        productionOrderItems.setProductionOrderByPoId(productionOrder);
                        productionOrderItemsList.add(productionOrderItems);
                        alreadyAddedProducts.add(product);
                    }
                }

                productionOrder.setProductionOrderItems(productionOrderItemsList);
                databaseManager.writeProductionOrder(productionOrder);

                productionOrders.add(productionOrder);

                for (CustomerOrder customerOrder : productTypesTargetDate.getValue().getCustomerOrders()) {
                    if (customerOrder.getProductionOrders() == null) {
                        customerOrder.setProductionOrders(productionOrders);
                    } else {
                        for (ProductionOrder productionOrderToAdd : productionOrders) {
                            if (!customerOrder.getProductionOrders().contains(productionOrderToAdd)) {
                                customerOrder.getProductionOrders().add(productionOrderToAdd);
                            }
                        }
                    }

                }

                for (ProductionOrderItems productionOrderItems : productionOrder.getProductionOrderItems()) {
                    for (int i = 0; i < productionOrderItems.getCnt(); ++i) {
                        Production production = new Production();
                        production.setPrTimestamp(getPrTimestampForTargetDate(productTypesTargetDate.getValue().getTargetDate()));
                        production.setProductionOrderByProductionOrderId(productionOrder);
                        production.setMachineId(productionOrderItems.getProductByPId().getpId() / 10 + 1);
                        production.setToolId(new Random().nextInt(100) + 1);
                        production.setProductByProductId(productionOrderItems.getProductByPId());

                        databaseManager.writeProduction(production);
                    }
                }
            }
        }
        for (CustomerOrder customerOrder : customerOrders) {
            databaseManager.writeCustomerOrder(customerOrder);
        }
    }

    private Timestamp getPrTimestampForTargetDate(Date targetDate) {
        Date date = new Date(targetDate.getTime() + new Random().nextInt(86400000));

        return new Timestamp(date.getTime());
    }

}
