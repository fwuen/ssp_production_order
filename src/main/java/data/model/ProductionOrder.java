package data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "production_order")
public class ProductionOrder {
    @Id
    @Column(name = "po_id")
    @Getter
    @Setter
    private int id;

    @Column(name = "cu_id")
    @Getter
    @Setter
    private int customerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "production_order_items",
            joinColumns = @JoinColumn(name="p_id"))
    @Getter
    @Setter
    private List<Product> items;
    
    public ProductionOrder(int id, int customerId, List<Product> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    public ProductionOrder() {
    }
}
