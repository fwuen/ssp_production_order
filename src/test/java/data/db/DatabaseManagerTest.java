package data.db;

import data.model.Product;
import data.model.ProductType;
import data.model.Production;
import data.model.ProductionOrder;
import data.xml.XmlImporter;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManagerTest {

    @Test
    public void testWriteProductType() {
        DatabaseManager databaseManager = new DatabaseManager();
        ProductType productType = new ProductType();
        productType.setPtId(1);
        productType.setProductTypeByPtParentPtId(productType);
        databaseManager.writeProductType(productType);
    }

    @Test
    public void testWriteProductionOrder() {
        DatabaseManager databaseManager = new DatabaseManager();
        ProductionOrder productionOrder = new ProductionOrder();
        Product product = new Product();
        product.setProductTypeByProductId(databaseManager.findProductTypeById(1));
        product.setpName("Test1");
        product.setpId(1);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productionOrder.setProductionOrderItems(productList);
        productionOrder.setPoId(1);
        productionOrder.setCuId(1);
        databaseManager.writeProductionOrder(productionOrder);

        System.out.println(databaseManager.findProductionOrderById(1).getProductionOrderItems().get(0).getpName());
    }

    @Test
    public void writeProductTypeXmlData() {
        DatabaseManager databaseManager = new DatabaseManager();
        XmlImporter xmlImporter = new XmlImporter(new File("src\\main\\resources\\xml\\prod_type.xml"));
        List<ProductType> productTypes = new ArrayList<>();
        try {
            productTypes = xmlImporter.readProductTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ProductType productType:productTypes) {
            databaseManager.writeProductType(productType);
        }
    }

    @Test
    public void writeProductXmlData() {
        DatabaseManager databaseManager = new DatabaseManager();
        XmlImporter xmlImporter = new XmlImporter(new File("src\\main\\resources\\xml\\product.xml"));
        List<Product> products = new ArrayList<>();
        try {
            products = xmlImporter.readProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Product product:products) {
            databaseManager.writeProduct(product);
        }
    }

    @Test
    public void writeProductionOrderData() {
        DatabaseManager databaseManager = new DatabaseManager();
        XmlImporter xmlImporter = new XmlImporter(new File("src\\main\\resources\\xml\\production_order.xml"));
        List<ProductionOrder> productionOrders = new ArrayList<>();
        List<Product> allProducts = databaseManager.findAllProducts();
        try {
            productionOrders = xmlImporter.readProductionOrders(allProducts);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        for (ProductionOrder productionOrder : productionOrders) {
            databaseManager.writeProductionOrder(productionOrder);
        }
    }

    @Test
    public void writeProductionData() {
        DatabaseManager databaseManager = new DatabaseManager();
        XmlImporter xmlImporter = new XmlImporter(new File("src\\main\\resources\\xml\\production.xml"));
        List<Production> productions = new ArrayList<>();
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

        for (Production production : productions) {
            databaseManager.writeProduction(production);
        }
    }

    @Test
    public void testUpdateProduct() {
        DatabaseManager databaseManager = new DatabaseManager();
        Product product = databaseManager.findProductById(1);
        product.setpName("Product1");
        databaseManager.updateProduct(product);
    }
}
