package data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductionOrder {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int customerId;

    @Getter
    @Setter
    private List<ProductionOrderItem> items;
    
    public ProductionOrder(int id, int customerId, List<ProductionOrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }
}
