package data.xml;

import data.db.ProductProvider;
import data.db.ProductTypeProvider;
import data.db.ProductionOrderProvider;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XmlImporter {
    private File xmlFileToImport;

    ProductTypeProvider productTypeProvider = new ProductTypeProvider();

    ProductProvider productProvider = new ProductProvider();

    ProductionOrderProvider productionOrderProvider = new ProductionOrderProvider();

    public void setFile(File xmlFileToImport) {
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
            ProductType productType = new ProductType();
            productType.setPtId(Integer.parseInt(productTypeXml.getChildText("ProductTypeID")));
            if(Integer.parseInt(productTypeXml.getChildText("ParentProductTypeID")) == productType.getPtId()) {
                productType.setProductTypeByPtParentPtId(productType);
            } else {
                ProductType parentProductType = new ProductType();
                parentProductType.setPtId(Integer.parseInt(productTypeXml.getChildText("ParentProductTypeID")));
                productType.setProductTypeByPtParentPtId(parentProductType);
            }
    
            prodTypes.add(productType);
        });
        
        return prodTypes;
    }
    
    public List<Product> readProducts() throws Exception {
        List<Product> products = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();

        root.getChildren("Product").forEach((product) -> {
            ProductType pt = productTypeProvider.findProductTypeById(Integer.parseInt(product.getChildText("ProductTypeID")));
            Product productToAdd = new Product();
            productToAdd.setpId(Integer.parseInt(product.getChildText("ProductID")));
            productToAdd.setProductTypeByProductId(pt);
            productToAdd.setpName(product.getChildText("ProductName"));
            productToAdd.setpPrice(Double.parseDouble(product.getChildText("Price")));


            products.add(productToAdd);
        });
    
        return products;
    }

    public List<ProductionOrder> readProductionOrders(List<Product> products) throws IOException, SAXException, ParserConfigurationException {
        List<ProductionOrder> productionOrders = new ArrayList<>();

        Element root = getXmlDocument().getRootElement();
            
        root.getChildren("ProductionOrder").forEach((productionOrder) -> {
            List<Product> productionOrderItems = new ArrayList<>();

            productionOrder.getChild("ProductionOrderItems").getChildren("ProductionOrderItem").forEach((productionOrderItem) -> {
                productionOrderItems.add(products.get(Integer.parseInt(productionOrderItem.getChildText("ProductID"))-1));
            });

            ProductionOrder productionOrderToAdd = new ProductionOrder();
            productionOrderToAdd.setPoId(Integer.parseInt(productionOrder.getChildText("ProductionOrderID")));

            List<ProductionOrderItems> prodItems = new ArrayList();

            ProductionOrderItems currentProdOrderItems = null;

            List<Product> alreadyAddedProducts = new ArrayList<>();

            for(Product product : productionOrderItems) {
                if(alreadyAddedProducts.contains(product)) {
                    for(ProductionOrderItems prodOrderItems : prodItems) {
                        if(prodOrderItems.getProductByPId().getpId() == product.getpId()) {
                            currentProdOrderItems = prodItems.get(prodItems.indexOf(product));
                            currentProdOrderItems.setCnt(currentProdOrderItems.getCnt() + 1);
                        }
                    }
                } else {
                    currentProdOrderItems = new ProductionOrderItems();
                    currentProdOrderItems.setCnt(1);
                    currentProdOrderItems.setProductByPId(product);
                    currentProdOrderItems.setProductionOrderByPoId(productionOrderToAdd);
                    alreadyAddedProducts.add(product);
                }
            }
            productionOrderToAdd.setProductionOrderItems(prodItems);

            productionOrders.add(productionOrderToAdd);
        });
    
        return productionOrders;
    }
    
    public List<Production> readProductions() throws ParseException, IOException, SAXException, ParserConfigurationException {
        List<Production> productions = new ArrayList<>();
    
        Element root = getXmlDocument().getRootElement();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
        for (Element production : root.getChildren("Production")) {

            Product productToAdd = productProvider.findProductById(Integer.parseInt(production.getChild("Product").getChildText("ProductID")));

            ProductionOrder productionOrder = productionOrderProvider.findProductionOrderById(Integer.parseInt(production.getChildText("ProductionOrderID")));

            Production productionToAdd = new Production();
            productionToAdd.setMachineId(Integer.parseInt(production.getChildText("MachineID")));
            productionToAdd.setPrTimestamp(new Timestamp(format.parse(production.getChildText("ProductionDate")).getTime()));
            productionToAdd.setPrId(Integer.parseInt(production.getChildText("ProductionID")));
            productionToAdd.setToolId(Integer.parseInt(production.getChildText("ToolID")));
            productionToAdd.setProductByProductId(productToAdd);
            productionToAdd.setProductionOrderByProductionOrderId(productionOrder);

            productions.add(productionToAdd);
        }
    
        return productions;
    }
}
