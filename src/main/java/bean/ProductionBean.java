package bean;

import data.db.DatabaseManager;
import data.model.Production;

import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "productionBean")
public class ProductionBean {
    public List<Production> allProductions() {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProductions();
    }
}
