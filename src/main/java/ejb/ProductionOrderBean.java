package ejb;

import data.db.CustomerOrderProvider;
import data.db.ProductionOrderProvider;
import data.db.ProductionProvider;
import data.model.*;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import java.sql.Timestamp;
import java.util.*;

@Singleton
@Local(ProductionOrderLocal.class)
@Remote(ProductionOrder.class)
public class ProductionOrderBean implements ProductionOrder, ProductionOrderLocal {
    @EJB
    ProductionOrderProvider productionOrderProvider;

    @EJB
    CustomerOrderProvider customerOrderProvider;

    @EJB
    ProductionProvider productionProvider;

    @Override
    public void writeProductionOrders(List<Order> orders) {
        Map<ProductType, ProductTypesTargetDate> productTypesTargetDates = new HashMap<>();
        List<CustomerOrder> customerOrders = new ArrayList<>();

        for (Order order : orders) {
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrders.add(customerOrder);
            customerOrder.setTargetDate(new java.sql.Date(order.getTargetDate().getTime()));
            customerOrder.setCustomerId(order.getCustomerId());


            for (Product product : order.getProducts()) {
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
            }
        }


        List<Product> alreadyAddedProducts = new ArrayList<>();

        for (Map.Entry<ProductType, ProductTypesTargetDate> productTypesTargetDate : productTypesTargetDates.entrySet()) {
            if (productTypesTargetDate.getValue().getProducts().size() > 0) {
                List<data.model.ProductionOrder> productionOrders = new ArrayList<>();
                data.model.ProductionOrder productionOrder = new data.model.ProductionOrder();
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
                productionOrderProvider.writeProductionOrder(productionOrder);

                productionOrders.add(productionOrder);

                for (CustomerOrder customerOrder : productTypesTargetDate.getValue().getCustomerOrders()) {
                    if (customerOrder.getProductionOrders() == null) {
                        customerOrder.setProductionOrders(productionOrders);
                    } else {
                        for (data.model.ProductionOrder productionOrderToAdd : productionOrders) {
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

                        productionProvider.writeProduction(production);
                    }
                }
            }
        }
        for (CustomerOrder customerOrder : customerOrders) {
            customerOrderProvider.writeCustomerOrder(customerOrder);
        }
    }

    private Timestamp getPrTimestampForTargetDate(Date targetDate) {
        Date date = new Date(targetDate.getTime() + new Random().nextInt(86400000));

        return new Timestamp(date.getTime());
    }
}
