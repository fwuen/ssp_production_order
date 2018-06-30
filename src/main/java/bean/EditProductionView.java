package bean;

import data.db.ProductionOrderProvider;
import data.db.ProductionProvider;
import data.model.Product;
import data.model.Production;
import data.model.ProductionOrder;
import data.model.ProductionOrderItems;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean
@SessionScoped
public class EditProductionView {
    @Getter
    private int selectedProductionId;

    @Getter
    @Setter
    private Production selectedProduction;

    @Getter
    @Setter
    private int machine;

    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private int tool;

    @Getter
    @Setter
    private ProductionOrder productionOrder;

    @Getter
    @Setter
    private List<Production> productions;

    @EJB
    private ProductionProvider productionProvider;

    @EJB
    private ProductionOrderProvider productionOrderProvider;

    ResourceBundle msgs = ResourceBundle.getBundle("internationalization.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init() {
        productions = productionProvider.findAllProductions();
    }

    public void selectProduction() {
        FacesMessage msg;
        if (selectedProduction != null) {
            setDetailsInView();
            msg = new FacesMessage(msgs.getString("Selected"), selectedProduction.toString());
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgs.getString("InvalidProduction"), msgs.getString("NoProductionSelection"));
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void submitForm() {
        FacesMessage msg;
        try {
            selectedProduction.setToolId(tool);
            selectedProduction.setMachineId(machine);
            selectedProduction.setPrTimestamp(new Timestamp(date.getTime()));
            productionProvider.updateProduction(selectedProduction);
            msg = new FacesMessage(msgs.getString("Success"), msgs.getString("EditSuccess"));
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgs.getString("Error"), msgs.getString("EditError"));
        }
        productions = productionProvider.findAllProductions();

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteProduction() {
        FacesMessage msg;
        if (selectedProduction != null) {
            try {
                for (ProductionOrderItems productionOrderItems : selectedProduction.getProductionOrderByProductionOrderId().getProductionOrderItems()) {
                    if (productionOrderItems.getProductByPId().getpId() == selectedProduction.getProductByProductId().getpId()) {
                        if (productionOrderItems.getCnt() == 1) {
                            productionProvider.removeProduction(selectedProduction);
                            productionOrderProvider.removeProductionOrderItems(productionOrderItems);
                        } else {
                            productionProvider.removeProduction(selectedProduction);
                            productionOrderItems.setCnt(productionOrderItems.getCnt() - 1);
                            productionOrderProvider.updateProductionOrderItems(productionOrderItems);
                        }
                    }
                }
                msg = new FacesMessage(msgs.getString("Deleted"), msgs.getString("DeletedDetail"));
            } catch (Exception e) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgs.getString("DeletionFailed"), msgs.getString("DeletionFailedDetail"));
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgs.getString("InvalidProduction"), msgs.getString("NoProductionSelection"));
        }

        productions = productionProvider.findAllProductions();
        selectedProduction = null;
        setDetailsInView();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setSelectedProductionId(int selectedProductionId) {
        this.selectedProductionId = selectedProductionId;
        selectedProduction = productionProvider.findProductionById(selectedProductionId);
        selectProduction();
    }

    private void setDetailsInView() {
        if (selectedProduction != null) {
            machine = selectedProduction.getMachineId();
            product = selectedProduction.getProductByProductId();
            tool = selectedProduction.getToolId();
            productionOrder = selectedProduction.getProductionOrderByProductionOrderId();
            date = new Date(selectedProduction.getPrTimestamp().getTime());
        } else {
            machine = 1;
            product = null;
            tool = 1;
            productionOrder = null;
            date = null;
        }
    }
}
