package data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "production_order", schema = "production_order")
public class ProductionOrder {
    private int poId;
    private Integer coId;
    private List<Product> productionOrderItems;

    @Id
    @Column(name = "po_id")
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    @Basic
    @Column(name = "co_id")
    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer cuId) {
        this.coId = cuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionOrder that = (ProductionOrder) o;
        return poId == that.poId &&
                Objects.equals(coId, that.coId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(poId, coId);
    }

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "production_order_items", schema = "production_order",joinColumns = @JoinColumn(name = "po_id", referencedColumnName = "po_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "p_id", referencedColumnName = "p_id", nullable = false))
    public List<Product> getProductionOrderItems() {
        return productionOrderItems;
    }

    public void setProductionOrderItems(List<Product> productionOrderItems) {
        this.productionOrderItems = productionOrderItems;
    }
}
