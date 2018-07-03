package bean;

import data.db.CustomerOrderProvider;
import data.db.ProductionOrderProvider;
import data.model.CustomerOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "customerOrderOverview")
@RequestScoped
public class CustomerOrderOverview {
    @EJB
    CustomerOrderProvider customerOrderProvider;

    @Getter
    @Setter
    private Date start;
    @Getter
    @Setter
    private Date end;

    @Getter
    @Setter
    private List<CustomerOrder> customerOrders;

    @Getter
    @Setter
    private List<CustomerOrder> filteredOrders;

    @PostConstruct
    public void init() {
        customerOrders = customerOrderProvider.findAllCustomerOrders();
        filteredOrders = new ArrayList<>();
        for (CustomerOrder customerOrder : customerOrders) {
            filteredOrders.add(customerOrder);
        }
    }

    public void updateOrders() {
        filteredOrders.clear();
        if(start == null) start = new Date();
        if(end == null) end = new Date();
        for (CustomerOrder customerOrder : customerOrders) {
            if (customerOrder.getTargetDate().after(new Date(start.getTime()-86400)) && customerOrder.getTargetDate().before(new Date(end.getTime()+86400))) {
                filteredOrders.add(customerOrder);
            }
        }
    }
}
