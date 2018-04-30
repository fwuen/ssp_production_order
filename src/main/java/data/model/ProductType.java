package data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_type", schema = "production_order")
public class ProductType {
    private int ptId;
    private ProductType productTypeByPtParentPtId;

    @Id
    @Column(name = "pt_id")
    public int getPtId() {
        return ptId;
    }

    public void setPtId(int ptId) {
        this.ptId = ptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return ptId == that.ptId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ptId);
    }

    @ManyToOne
    @JoinColumn(name = "pt_parent_pt_id", referencedColumnName = "pt_id", nullable = false)
    public ProductType getProductTypeByPtParentPtId() {
        return productTypeByPtParentPtId;
    }

    public void setProductTypeByPtParentPtId(ProductType productTypeByPtParentPtId) {
        this.productTypeByPtParentPtId = productTypeByPtParentPtId;
    }
}
