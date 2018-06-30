package bean;

import data.db.CustomerOrderProvider;
import data.db.ProductionOrderProvider;
import data.model.CustomerOrder;
import data.model.ProductionOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name = "customerOrderOverview")
@RequestScoped
public class CustomerOrderOverview {
    @EJB
    CustomerOrderProvider customerOrderProvider;

    @Getter
    @Setter
    private List<CustomerOrder> customerOrders;

    @PostConstruct
    public void init() {
        customerOrders = customerOrderProvider.findAllCustomerOrders();
    }
}
