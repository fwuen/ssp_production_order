package data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class Order {
    @Getter
    @Setter
    private int customerId;

    @Getter
    @Setter
    private List<Product> products;

    @Getter
    @Setter
    Date targetDate;

}
