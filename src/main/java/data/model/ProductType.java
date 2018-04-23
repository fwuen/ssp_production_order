package data.model;

public class ProductType {
    
    private int id;
    
    private int parentProductTypeId;
    
    public ProductType(int id, int parentProductTypeId) {
        this.id = id;
        this.parentProductTypeId = parentProductTypeId;
    }
}
