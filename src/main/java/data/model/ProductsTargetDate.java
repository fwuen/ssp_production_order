package data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ProductsTargetDate {
    @Getter
    @Setter
    private int productId;

    @Getter
    @Setter
    private int numberOfOrderedProducts;

    @Getter
    @Setter
    private Date targetDate;
}
