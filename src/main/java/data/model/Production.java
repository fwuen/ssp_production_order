package data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

public class Production {
    @Id
    @Column(name = "pr_id")
    @Getter
    @Setter
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Getter
    @Setter
    private Product product;
    
    @Column(name = "machine_id")
    @Getter
    @Setter
    private int machineId;
    
    @Column(name = "tool_id")
    @Getter
    @Setter
    private int toolId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id")
    @Getter
    @Setter
    private ProductionOrder productionOrder;

    @Column(name = "pr_date")
    @Getter
    @Setter
    private Date productionDate;
    
    public Production(int id, Product product, int machineId, int toolId, ProductionOrder productionOrder, Date productionDate) {
        this.id = id;
        this.product = product;
        this.machineId = machineId;
        this.toolId = toolId;
        this.productionOrder = productionOrder;
        this.productionDate = productionDate;
    }
}
