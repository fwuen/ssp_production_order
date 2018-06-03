package ejb;

import data.model.Order;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ProductionOrder {
    void writeProductionOrders(List<Order> orders);
}
