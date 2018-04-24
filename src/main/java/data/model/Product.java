package data.model;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String productName;

    @Getter
    @Setter
    private int productTypeId;
    
    public Product(int id, String productName, int productTypeId) {
        this.id = id;
        this.productName = productName;
        this.productTypeId = productTypeId;
    }
}
