package data.model;

import lombok.Getter;
import lombok.Setter;

public class ProductType {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int parentProductTypeId;
    
    public ProductType(int id, int parentProductTypeId) {
        this.id = id;
        this.parentProductTypeId = parentProductTypeId;
    }
}
