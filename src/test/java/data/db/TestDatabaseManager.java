package data.db;

import data.model.ProductType;
import org.junit.Test;

public class TestDatabaseManager {
    @Test
    public void testWriteProductType() {
        DatabaseManager databaseManager = new DatabaseManager();
        ProductType productType = new ProductType();
        productType.setId(1);
        productType.setParentProductType(productType);
        databaseManager.writeProductType(productType);
    }
}
