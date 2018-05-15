package logic;

import data.model.Order;
import data.model.Product;
import data.model.ProductsTargetDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductionOrderManager {
    private void createProductionOrdersFromCustomerOrders(List<Order> orders) {
        List<ProductsTargetDate> productsTargetDates = new ArrayList<>();
        boolean productFound = false;
        ProductsTargetDate productsTargetDateToWorkWith = null;

        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                for (ProductsTargetDate productsTargetDate : productsTargetDates) {
                    if (productsTargetDate.getProductId() == product.getpId()) {
                        productFound = true;
                        productsTargetDateToWorkWith = productsTargetDate;
                        break;
                    }
                }
                if(!productFound) {
                    productsTargetDateToWorkWith = new ProductsTargetDate();
                }
                productsTargetDateToWorkWith.setNumberOfOrderedProducts(productsTargetDateToWorkWith.getNumberOfOrderedProducts() + 1);
                productsTargetDateToWorkWith.setProductId(product.getpId());
            }
        }
    }

    private Date findEarliestDate(List<Order> orders) {
        Date earliest = null;
        for (Order order : orders) {
            if (earliest == null) {
                earliest = order.getTargetDate();
            }
            else {
                if (order.getTargetDate().before(earliest)) {
                    earliest = order.getTargetDate();
                }
            }
        }
        return earliest;
    }

}
