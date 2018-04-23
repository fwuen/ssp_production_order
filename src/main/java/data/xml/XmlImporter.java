package data.xml;

import data.model.*;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XmlImporter {
    private File xmlFileToImport;

    public XmlImporter(File xmlFileToImport) {
        this.xmlFileToImport = xmlFileToImport;
    }
    
    private org.jdom2.Document getXmlDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(this.xmlFileToImport);
        DOMBuilder domBuilder = new DOMBuilder();
        return domBuilder.build(doc);
    }
    
    public List<ProductType> readProductTypes() throws Exception {
        List<ProductType> prodTypes = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
    
        root.getChildren("ProductType").forEach((productionType) -> prodTypes.add(
          new ProductType(
                  Integer.parseInt(productionType.getChildText("ProductTypeID")),
                  Integer.parseInt(productionType.getChildText("ParentProductTypeID"))
                  )));
    
        return prodTypes;
    }
    
    public List<Product> readProducts() throws Exception {
        List<Product> products = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
    
        root.getChildren("Product").forEach((product) -> products.add(
          new Product(
                  Integer.parseInt(product.getChildText("ProductID")),
                  Integer.parseInt(product.getChildText("ProductTypeID"))
          )));
    
        return products;
    }
    
    public List<ProductionOrder> readProductionOrders() throws IOException, SAXException, ParserConfigurationException {
        List<ProductionOrder> productionOrders = new ArrayList<>();
        List<ProductionOrderItem> productionOrderItems = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
            
        root.getChildren("ProductionOrder").forEach((productionOrder) -> {
            productionOrder.getChildren("ProductionOrderItem").forEach((productionOrderItem) -> {
                productionOrderItems.add(
                        new ProductionOrderItem(
                                Integer.parseInt(productionOrderItem.getChildText("ProductID"))
                        ));
            });
            
            productionOrders.add(
                    new ProductionOrder(
                            Integer.parseInt(productionOrder.getChildText("ProductionOrderID")),
                            Integer.parseInt(productionOrder.getChildText("CustomerID")),
                            productionOrderItems
                    ));
        });
    
        return productionOrders;
    }
    
    public List<Production> readProductions() throws ParseException, IOException, SAXException, ParserConfigurationException {
        List<Production> productions = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
    
        for (Element production : root.getChildren("Production")) {
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");

            productions.add(
                    new Production(
                            Integer.parseInt(production.getChildText("ProductionID")),
                            Integer.parseInt(production.getChildText("ProductID")),
                            Integer.parseInt(production.getChildText("MachineID")),
                            Integer.parseInt(production.getChildText("ToolID")),
                            Integer.parseInt(production.getChildText("ProductionOrderID")),
                            format.parse(production.getChildText("ProductionDate"))
                    )
            );
        }
    
        return productions;
    }
}
