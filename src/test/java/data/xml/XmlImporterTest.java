package data.xml;

import data.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class XmlImporterTest {
    private String filePath = "W:\\ssp_data\\";
    
    @Test
    public void testReadProducts() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "product.xml"));
        List<Product> products = null;
        try {
            products = xmlImporter.readProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        Assert.assertNotNull(products);
        products.forEach(product ->
                System.out.println(product.getId())
        );
        
    }
}
