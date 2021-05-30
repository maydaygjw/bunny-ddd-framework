package xyz.mayday.tools.bunny.ddd.sample.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
public class OrderEntity {

    @Id
    String id;

    String userId;

    @Embedded
    OrderItemEntity orderItems;

}
