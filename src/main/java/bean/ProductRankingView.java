package bean;

import data.db.ProductProvider;
import data.db.ProductionProvider;
import data.model.Product;
import data.model.Production;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "productRankingView")
@ViewScoped
public class ProductRankingView {
    @EJB
    ProductionProvider productionProvider;

    @EJB
    ProductProvider productProvider;

    @Getter
    @Setter
    private HorizontalBarChartModel horizontalBarChartModel;

    private List<Product> products;

    private List<Production> productions;

    ResourceBundle msgs = ResourceBundle.getBundle("internationalization.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init() {
        products = allProducts();
        productions = allProductions();
        createBarModels();
    }

    private void createBarModels() {
        horizontalBarChartModel = new HorizontalBarChartModel();

        ChartSeries productsChartSeries = new ChartSeries();

        for (Product product : products) {
            productsChartSeries.set(product.getpName(), getNumberOfProductionsForProductById(product.getpId()));
        }

        horizontalBarChartModel.addSeries(productsChartSeries);
        horizontalBarChartModel.setTitle(msgs.getString("ProductRanking"));
        Axis xAxis = horizontalBarChartModel.getAxis(AxisType.X);
        xAxis.setLabel(msgs.getString("Productions"));

        Axis yAxis = horizontalBarChartModel.getAxis(AxisType.Y);
        yAxis.setLabel(msgs.getString("Products"));
    }

    private List<Product> allProducts() {
        return productProvider.findAllProducts();
    }

    private List<Production> allProductions() {
        return productionProvider.findAllProductions();
    }

    private int getNumberOfProductionsForProductById(int productId) {
        int cnt = 0;
        for (Production production : productions) {
            if (production.getProductByProductId().getpId() == productId) {
                ++cnt;
            }
        }
        return cnt;
    }
}
