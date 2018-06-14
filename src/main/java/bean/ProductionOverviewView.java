package bean;

import data.db.ProductionOrderProvider;
import data.db.ProductionProvider;
import data.model.Production;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "productionOverviewView")
@ViewScoped
public class ProductionOverviewView {

    @Getter
    @Setter
    private ScheduleModel eventModel;

    @Getter
    @Setter
    private ScheduleEvent event = new DefaultScheduleEvent();

    @Getter
    @Setter
    private List<Production> productions;

    @Getter
    @Setter
    private List<Production> productionsOrderedByDate;

    @EJB
    private ProductionProvider productionProvider;

    @PostConstruct
    public void init() {
        productions = allProductions();
        productionsOrderedByDate = allProductionsOrderedByDate();

        eventModel = new DefaultScheduleModel();

        for (Production production : productions) {
            eventModel.addEvent(new DefaultScheduleEvent(production.getPrId() + ": " + production.getProductByProductId().getpName(), new Date(production.getPrTimestamp().getTime()), new Date(production.getPrTimestamp().getTime())));
        }
    }

    private List<Production> allProductionsOrderedByDate() {
        return productionProvider.findProductionsByProductionDate();
    }

    public List<Production> allProductions() {
        return productionProvider.findAllProductions();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        StringBuilder numberAsString = new StringBuilder();

        for (int i = 0; i < event.getTitle().length(); ++i) {
            if (Character.isDigit(event.getTitle().charAt(i))) {
                numberAsString.append(event.getTitle().charAt(i));
            } else {
                break;
            }
        }
        redirectToEdit(Integer.parseInt(numberAsString.toString()));

    }

    public void redirectToEdit(int id) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_from_reference.xhtml?id=" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
