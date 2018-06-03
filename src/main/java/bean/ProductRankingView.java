package bean;

import data.db.DatabaseManager;
import data.model.Product;
import data.model.Production;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "productRankingView")
@ViewScoped
public class ProductRankingView {
    @Getter
    @Setter
    private HorizontalBarChartModel horizontalBarChartModel;

    private List<Product> products = allProducts();

    private List<Production> productions = allProductions();

    ResourceBundle msgs = ResourceBundle.getBundle("internationalization.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init() {
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
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProducts();
    }

    private List<Production> allProductions() {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProductions();
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
