package xyz.mayday.tools.bunny.ddd.sample.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Embeddable
public class OrderItemEntity {

    @Id
    String id;

    String productName;

    BigDecimal price;
}
