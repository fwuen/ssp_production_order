package ejb;

import data.model.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductionOrderLocal {
    void writeProductionOrders(List<Order> orders);
}
