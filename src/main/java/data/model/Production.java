package data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Production {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int productId;

    @Getter
    @Setter
    private int machineId;

    @Getter
    @Setter
    private int toolId;

    @Getter
    @Setter
    private int productionOrderId;

    @Getter
    @Setter
    private Date productionDate;
    
    public Production(int id, int productId, int machineId, int toolId, int productionOrderId, Date productionDate) {
        this.id = id;
        this.productId = productId;
        this.machineId = machineId;
        this.toolId = toolId;
        this.productionOrderId = productionOrderId;
        this.productionDate = productionDate;
    }
}
