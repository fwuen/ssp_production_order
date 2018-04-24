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
    
        root.getChildren("ProductType").forEach((productTypeXml) -> {
            ProductType productType = new ProductType(Integer.parseInt(productTypeXml.getChildText("ProductTypeID")));
            if(Integer.parseInt(productTypeXml.getChildText("ParentProductTypeID")) == productType.getId()) {
                productType.setParentProductType(productType);
            } else {
                productType.setParentProductType(new ProductType(Integer.parseInt(productTypeXml.getChildText("ParentProductTypeID"))));
            }
    
            prodTypes.add(productType);
        });
        
        return prodTypes;
    }
    
    public List<Product> readProducts() throws Exception {
        List<Product> products = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
    
        root.getChildren("Product").forEach((product) -> products.add(
          new Product(
                  Integer.parseInt(product.getChildText("ProductID")),
                  product.getChildText("ProductName"),
                  new ProductType(Integer.parseInt(product.getChildText("ProductTypeID")))
          )));
    
        return products;
    }
    
    public List<ProductionOrder> readProductionOrders(List<Product> products) throws IOException, SAXException, ParserConfigurationException {
        List<ProductionOrder> productionOrders = new ArrayList<>();
        List<Product> productionOrderItems = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();
            
        root.getChildren("ProductionOrder").forEach((productionOrder) -> {
            productionOrder.getChildren("ProductionOrderItem").forEach((productionOrderItem) -> {
                productionOrderItems.add(products.get(Integer.parseInt(productionOrderItem.getChildText("ProductID"))-1));
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
    
    public List<Production> readProductions(List<ProductionOrder> productionOrders) throws ParseException, IOException, SAXException, ParserConfigurationException {
        List<Production> productions = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
        for (Element production : root.getChildren("Production")) {

            productions.add(
                    new Production(
                            Integer.parseInt(production.getChildText("ProductionID")),
                            new Product(
                                    Integer.parseInt(production.getChild("Product").getChildText("ProductID")),
                                    production.getChild("Product").getChildText("ProductName"),
                                    new ProductType(Integer.parseInt(production.getChild("Product").getChildText("ProductTypeID")))
                            ),
                            Integer.parseInt(production.getChildText("MachineID")),
                            Integer.parseInt(production.getChildText("ToolID")),
                            productionOrders.get(Integer.parseInt(production.getChildText("ProductionOrderID"))-1),
                            format.parse(production.getChildText("ProductionDate"))
                    )
            );
        }
    
        return productions;
    }
}
