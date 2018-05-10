package data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customer_order", schema = "production_order")
public class CustomerOrder {
    private int coId;
    private Integer customerId;
    private List<ProductionOrder> productionOrders;

    @Id
    @Column(name = "co_id")
    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    @Basic
    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return coId == that.coId &&
                Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(coId, customerId);
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "production_customer_order", schema = "production_order", joinColumns = @JoinColumn(name = "co_id", referencedColumnName = "co_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "po_id", referencedColumnName = "po_id", nullable = false))
    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }

    public void setProductionOrders(List<ProductionOrder> productionOrders) {
        this.productionOrders = productionOrders;
    }
}
