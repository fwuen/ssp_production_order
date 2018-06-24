package data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "production_order_items", schema = "production_order")
@IdClass(ProductionOrderItemsPK.class)
public class ProductionOrderItems {
    private int cnt;
    private int pId;
    private int poId;
    private Product productByPId;
    private ProductionOrder productionOrderByPoId;

    @Id
    @Column(name = "p_id", updatable=false, insertable=false)
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Id
    @Column(name = "po_id", updatable=false, insertable=false)
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    @Basic
    @Column(name = "cnt")
    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionOrderItems that = (ProductionOrderItems) o;
        return pId == that.pId &&
                poId == that.poId &&
                Objects.equals(cnt, that.cnt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pId, poId, cnt);
    }

    @ManyToOne
    @JoinColumn(name = "p_id", referencedColumnName = "p_id", nullable = false)
    @JsonIgnore
    public Product getProductByPId() {
        return productByPId;
    }

    public void setProductByPId(Product productByPId) {
        this.productByPId = productByPId;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "po_id", referencedColumnName = "po_id", nullable = false)
    @JsonIgnore
    public ProductionOrder getProductionOrderByPoId() {
        return productionOrderByPoId;
    }

    public void setProductionOrderByPoId(ProductionOrder productionOrderByPoId) {
        this.productionOrderByPoId = productionOrderByPoId;
    }
}
