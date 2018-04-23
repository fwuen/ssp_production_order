package data.xml;

import data.model.Product;
import data.model.Production;
import data.model.ProductionOrder;
import data.model.ProductionType;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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
    
    public List<ProductionType> readProductionTypes() {
        List<ProductionType> prodTypes = new ArrayList<>();
    
        try {
            Element root = getXmlDocument().getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return prodTypes;
    }
    
    public List<Product> readProducts() {
        List<Product> products = new ArrayList<>();
    
        try {
            Element root = getXmlDocument().getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return products;
    }
    
    public List<ProductionOrder> readProductionOrders() {
        List<ProductionOrder> prodoctuionOrders = new ArrayList<>();
    
        try {
            Element root = getXmlDocument().getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return prodoctuionOrders;
    }
    
    public List<Production> readProductions() {
        List<Production> productions = new ArrayList<>();
    
        try {
            Element root = getXmlDocument().getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return productions;
    }
}
