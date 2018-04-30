package data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @Column(name = "p_id")
    @Getter
    @Setter
    private int id;

    @Column(name = "p_name")
    @Getter
    @Setter
    private String productName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "p_type_id", referencedColumnName = "pt_id")
    @Getter
    @Setter
    private ProductType productType;
    
    public Product(int id, String productName, ProductType productType) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
    }

    public Product() {
    }
}
