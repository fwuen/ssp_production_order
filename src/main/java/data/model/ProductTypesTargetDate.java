package data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTypesTargetDate {
    @Getter
    @Setter
    private List<Product> products = new ArrayList<>();

    @Getter
    @Setter
    private ProductType productType;

    @Getter
    @Setter
    private Date targetDate;

    @Getter
    @Setter
    private List<CustomerOrder> customerOrders = new ArrayList<>();
}
