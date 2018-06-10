package data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "production", schema = "production_order")
@TableGenerator(name="production_table_generator", initialValue = 377, allocationSize = 1)
public class Production {
    private int prId;
    private int machineId;
    private int toolId;
    private Timestamp prTimestamp;
    private Product productByProductId;
    private ProductionOrder productionOrderByProductionOrderId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator = "production_table_generator")
    @Column(name = "pr_id")
    public int getPrId() {
        return prId;
    }

    public void setPrId(int prId) {
        this.prId = prId;
    }

    @Basic
    @Column(name = "machine_id")
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    @Basic
    @Column(name = "tool_id")
    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    @Basic
    @Column(name = "pr_date")
    public Timestamp getPrTimestamp() {
        return prTimestamp;
    }

    public void setPrTimestamp(Timestamp prDate) {
        this.prTimestamp = prDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        return prId == that.prId &&
                machineId == that.machineId &&
                toolId == that.toolId &&
                Objects.equals(prTimestamp, that.prTimestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prId, machineId, toolId, prTimestamp);
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "p_id", nullable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }

    @ManyToOne
    @JoinColumn(name = "production_order_id", referencedColumnName = "po_id", nullable = false)
    public ProductionOrder getProductionOrderByProductionOrderId() { return productionOrderByProductionOrderId; }

    public void setProductionOrderByProductionOrderId(ProductionOrder productionOrderByProductionOrderId) {
        this.productionOrderByProductionOrderId = productionOrderByProductionOrderId;
    }

    @Override
    public String toString() {
        return "Production " + this.getPrId();
    }
}
