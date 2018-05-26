package bean;

import data.db.DatabaseManager;
import data.model.Production;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    private List<Production> productions = allProductions();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();

        for (Production production : productions) {
            eventModel.addEvent(new DefaultScheduleEvent(production.getPrId() + ": " + production.getProductByProductId().getpName(), new Date(production.getPrTimestamp().getTime()), new Date(production.getPrTimestamp().getTime())));
        }
    }

    public List<Production> allProductions() {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProductions();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
