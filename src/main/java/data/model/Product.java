package data.model;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter
    @Setter
    private int id;
    
    @Getter
    @Setter
    private int productTypeId;
    
    public Product(int id, int productTypeId) {
        this.id = id;
        this.productTypeId = productTypeId;
    }
}
