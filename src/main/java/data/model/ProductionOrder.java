package data.model;

import java.util.List;

public class ProductionOrder {
    private int id;
    
    private int customerId;
    
    private List<ProductionOrderItem> items;
    
    public ProductionOrder(int id, int customerId, List<ProductionOrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }
}
