package at.flo.springauth.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "orders")
@Transactional
@Data
@ToString(exclude = {"user"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    private Integer amount;

    // column user_id is a foreign key to the user table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Order(String productName, Integer amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public Order() {}

}
