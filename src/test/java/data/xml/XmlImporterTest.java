package data.xml;

import data.model.Product;
import data.model.ProductType;
import data.model.Production;
import data.model.ProductionOrder;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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
                System.out.println(product.getId())
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
                System.out.println(productType.getId())
        );
    }

    @Test
    public void testReadProductionOrders() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "production_order.xml"));
        List<ProductionOrder> productionOrders = null;

        try {
            productionOrders = xmlImporter.readProductionOrders();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(productionOrders);
        productionOrders.forEach(productionOrder ->
                System.out.println(productionOrder.getId())
        );
    }

    @Test
    public void readProductions() {
        XmlImporter xmlImporter = new XmlImporter(new File(filePath + "production.xml"));
        List<Production> productions = null;

        try {
            productions = xmlImporter.readProductions();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(productions);
        productions.forEach(production -> {
            System.out.println(production.getId());
            System.out.println(production.getProductionDate());
        });
    }
}
