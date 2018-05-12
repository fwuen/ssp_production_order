package data.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "production_order", schema = "production_order")
@EqualsAndHashCode
public class ProductionOrder {
    private int poId;
    private List<Product> productionOrderItems;
    private List<CustomerOrder> customerOrders;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "po_id")
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "production_order_items", schema = "production_order",joinColumns = @JoinColumn(name = "po_id", referencedColumnName = "po_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "p_id", referencedColumnName = "p_id", nullable = false))
    public List<Product> getProductionOrderItems() {
        return productionOrderItems;
    }

    public void setProductionOrderItems(List<Product> productionOrderItems) {
        this.productionOrderItems = productionOrderItems;
    }

    @ManyToMany(mappedBy = "productionOrders", cascade = {CascadeType.MERGE})
    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
