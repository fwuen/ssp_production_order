package data.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ProductionOrderItemsPK implements Serializable {
    private int pId;
    private int poId;

    @Column(name = "p_id")
    @Id
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Column(name = "po_id")
    @Id
    public int getPoId() {
        return poId;
    }

    public void setPoId(int poId) {
        this.poId = poId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionOrderItemsPK that = (ProductionOrderItemsPK) o;
        return pId == that.pId &&
                poId == that.poId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pId, poId);
    }
}
