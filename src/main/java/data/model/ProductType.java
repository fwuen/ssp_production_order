package data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @Column(name = "pt_id")
    @Getter
    @Setter
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_parent_pt_id", referencedColumnName = "pt_id")
    @Getter
    @Setter
    private ProductType parentProductType;
    
    public ProductType(int id) {
        this.id = id;
    }

    public ProductType() {
    }
}
