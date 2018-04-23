package data.model;

import java.util.Date;

public class Production {
    private int id;
    
    private int productId;
    
    private int machineId;
    
    private int toolId;
    
    private int productionOrderId;
    
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
