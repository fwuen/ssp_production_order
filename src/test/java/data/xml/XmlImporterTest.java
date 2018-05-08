package data.xml;

import data.model.Product;
import data.model.ProductType;
import data.model.Production;
import data.model.ProductionOrder;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.util.List;

public class XmlImporterTest {
    private String filePath = "C:\\workspace\\ssp_data\\";
    
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
                System.out.println(product.getpId())
        );
    }

    @Test
    public void testReadProductTypes() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "prod_type.xml"));
        List<ProductType> productTypes = null;

        try {
            productTypes = xmlImporter.readProductTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(productTypes);
        productTypes.forEach(productType ->
                System.out.println(productType.getPtId())
        );
    }

    @Test
    public void testReadProductionOrders() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "production_order.xml"));
        List<ProductionOrder> productionOrders = null;

        try {
            productionOrders = xmlImporter.readProductionOrders(xmlImporter.readProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(productionOrders);
        productionOrders.forEach(productionOrder ->
                System.out.println(productionOrder.getPoId())
        );
    }

    @Test
    public void readProductions() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "production.xml"));
        List<Production> productions = null;

        try {
            productions = xmlImporter.readProductions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(productions);
        productions.forEach(production -> {
            System.out.println(production.getPrId());
            System.out.println(production.getPrTimestamp());
        });
    }
}
