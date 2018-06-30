package data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "production_order", schema = "production_order")
@EqualsAndHashCode
@TableGenerator(name="table_generator", initialValue = 51, allocationSize = 1)
public class ProductionOrder {
    private int poId;
    private List<CustomerOrder> customerOrders;
    private List<ProductionOrderItems> productionOrderItems;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator = "table_generator")
    @Column(name = "po_id")
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    @OneToMany(mappedBy = "productionOrderByPoId", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ProductionOrderItems> getProductionOrderItems() { return productionOrderItems; }

    public void setProductionOrderItems(List<ProductionOrderItems> productionOrderItems) { this.productionOrderItems = productionOrderItems; }


    @ManyToMany(mappedBy = "productionOrders", cascade = {CascadeType.MERGE})
    @JsonIgnore
    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
