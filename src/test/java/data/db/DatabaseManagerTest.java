package data.db;

import data.model.*;
import data.xml.XmlImporter;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManagerTest {

    ProductProvider productProvider = new ProductProvider();

    ProductTypeProvider productTypeProvider = new ProductTypeProvider();

    ProductionOrderProvider productionOrderProvider = new ProductionOrderProvider();

    ProductionProvider productionProvider = new ProductionProvider();

    XmlImporter xmlImporter = new XmlImporter();

    @Test
    public void testWriteProductType() {
        ProductType productType = new ProductType();
        productType.setPtId(1);
        productType.setProductTypeByPtParentPtId(productType);
        productTypeProvider.writeProductType(productType);
    }

    @Test
    public void writeProductTypeXmlData() {
        xmlImporter.setFile(new File("src\\main\\resources\\xml\\prod_type.xml"));
        List<ProductType> productTypes = new ArrayList<>();
        try {
            productTypes = xmlImporter.readProductTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ProductType productType:productTypes) {
            productTypeProvider.writeProductType(productType);
        }
    }

    @Test
    public void writeProductXmlData() {
        xmlImporter.setFile(new File("src\\main\\resources\\xml\\product.xml"));
        List<Product> products = new ArrayList<>();
        try {
            products = xmlImporter.readProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Product product:products) {
            productProvider.writeProduct(product);
        }
    }

    @Test
    public void writeProductionOrderData() {
        xmlImporter.setFile(new File("src\\main\\resources\\xml\\production_order.xml"));
        List<ProductionOrder> productionOrders = new ArrayList<>();
        List<Product> allProducts = productProvider .findAllProducts();
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
            for (ProductionOrderItems productionOrderItems : productionOrder.getProductionOrderItems()) {
                productionOrderProvider.writeProductionOrderItems(productionOrderItems);
            }
            productionOrderProvider.writeProductionOrder(productionOrder);
        }
    }

    @Test
    public void writeProductionData() {
        xmlImporter.setFile(new File("src\\main\\resources\\xml\\production.xml"));
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
            productionProvider.writeProduction(production);
        }
    }

    @Test
    public void testUpdateProduct() {
        Product product = productProvider.findProductById(1);
        product.setpName("Product1");
        productProvider.updateProduct(product);
    }
}
