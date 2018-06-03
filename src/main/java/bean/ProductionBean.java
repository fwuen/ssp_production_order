package bean;

import data.db.DatabaseManager;
import data.model.Production;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

//TODO Refactor
@ManagedBean(name = "productionBean")
@ViewScoped
public class ProductionBean {
    private TimelineModel model;

    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = false;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;

    @Getter
    @Setter
    private List<Production> productions = allProductions();

    ResourceBundle msgs = ResourceBundle.getBundle("internationalization.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    protected void initialize() {
        model = new TimelineModel();
        Calendar calendar = Calendar.getInstance();
        for (Production production : productions) {
            calendar.setTimeInMillis(production.getPrTimestamp().getTime());
            model.add(new TimelineEvent(production.getProductByProductId().getpName() + " | " + msgs.getString("ProdOrder") + " #" + production.getProductionOrderByProductionOrderId().getPoId(), calendar.getTime()));
        }
    }

    public List<Production> allProductions() {
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.findAllProductions();
    }

    public void onSelect(TimelineSelectEvent e) {
        TimelineEvent timelineEvent = e.getTimelineEvent();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, msgs.getString("SelectedEvent"), timelineEvent.getData().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public TimelineModel getModel() {
        return model;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isZoomable() {
        return zoomable;
    }

    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isStackEvents() {
        return stackEvents;
    }

    public void setStackEvents(boolean stackEvents) {
        this.stackEvents = stackEvents;
    }

    public String getEventStyle() {
        return eventStyle;
    }

    public void setEventStyle(String eventStyle) {
        this.eventStyle = eventStyle;
    }

    public boolean isAxisOnTop() {
        return axisOnTop;
    }

    public void setAxisOnTop(boolean axisOnTop) {
        this.axisOnTop = axisOnTop;
    }

    public boolean isShowCurrentTime() {
        return showCurrentTime;
    }

    public void setShowCurrentTime(boolean showCurrentTime) {
        this.showCurrentTime = showCurrentTime;
    }

    public boolean isShowNavigation() {
        return showNavigation;
    }

    public void setShowNavigation(boolean showNavigation) {
        this.showNavigation = showNavigation;
    }
}
