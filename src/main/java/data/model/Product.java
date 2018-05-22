package data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "production_order")
public class Product {
    private int pId;
    private ProductType productType;
    private String pName;
    private List<ProductionOrderItems> productionOrderItems;
    private Double pPrice;

    @Id
    @Column(name = "p_id")
    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Basic
    @Column(name = "p_name")
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "p_type_id", referencedColumnName = "pt_id", nullable = false)
    public ProductType getProductTypeByProductId() {
        return productType;
    }

    public void setProductTypeByProductId(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return pId == that.pId &&
                Objects.equals(pName, that.pName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pId, pName);
    }

    /*
    @ManyToMany(mappedBy = "productionOrderItems")
    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }

    public void setProductionOrders(List<ProductionOrder> productionOrders) {
        this.productionOrders = productionOrders;
    }*/

    @OneToMany(mappedBy = "productByPId")
    public List<ProductionOrderItems> getProductionOrderItems() { return this.productionOrderItems; }

    public void setProductionOrderItems(List<ProductionOrderItems> productionOrderItems) { this.productionOrderItems = productionOrderItems; }

    @Basic
    @Column(name = "p_price")
    public Double getpPrice() {
        return pPrice;
    }

    public void setpPrice(Double pPrice) {
        this.pPrice = pPrice;
    }
    /*
    @Override
    public String toString() {
        return this.pName;
    }*/
}
