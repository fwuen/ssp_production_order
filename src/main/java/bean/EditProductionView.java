package bean;

import data.db.DatabaseManager;
import data.model.Product;
import data.model.Production;
import data.model.ProductionOrder;
import data.model.ProductionOrderItems;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.Timestamp;
import java.util.Date;

@ManagedBean
@SessionScoped
public class EditProductionView {
    @Getter
    @Setter
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

    public void selectProduction() {
        machine = selectedProduction.getMachineId();
        product = selectedProduction.getProductByProductId();
        tool = selectedProduction.getToolId();
        productionOrder = selectedProduction.getProductionOrderByProductionOrderId();
        date = new Date(selectedProduction.getPrTimestamp().getTime());

        FacesMessage msg;
        if (selectedProduction != null) {
            msg = new FacesMessage("Selected", selectedProduction.toString());
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "No production selected!");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void submitForm() {
        selectedProduction.setToolId(tool);
        selectedProduction.setMachineId(machine);
        selectedProduction.setPrTimestamp(new Timestamp(date.getTime()));
        new DatabaseManager().updateProduction(selectedProduction);
    }

    public void deleteProduction() {
        DatabaseManager databaseManager = new DatabaseManager();
        for (ProductionOrderItems  productionOrderItems : selectedProduction.getProductionOrderByProductionOrderId().getProductionOrderItems()) {
            if (productionOrderItems.getProductByPId().getpId() == selectedProduction.getProductByProductId().getpId()) {
                if (selectedProduction.getProductionOrderByProductionOrderId().getProductionOrderItems().size() == 1) {
                    if (productionOrderItems.getCnt() == 1) {
                        databaseManager.removeProduction(selectedProduction);
                        databaseManager.removeProductionOrder(selectedProduction.getProductionOrderByProductionOrderId());
                    } else {
                        databaseManager.removeProduction(selectedProduction);
                        productionOrderItems.setCnt(productionOrderItems.getCnt() - 1);
                        databaseManager.updateProductionOrderItems(productionOrderItems);
                    }
                } else {
                    if (productionOrderItems.getCnt() == 1) {
                        databaseManager.removeProduction(selectedProduction);
                        databaseManager.removeProductionOrderItems(productionOrderItems);
                    } else {
                        databaseManager.removeProduction(selectedProduction);
                        productionOrderItems.setCnt(productionOrderItems.getCnt() - 1);
                        databaseManager.updateProductionOrderItems(productionOrderItems);
                    }
                }
            }
        }
    }
}
