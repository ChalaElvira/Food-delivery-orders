package org.elvira.fooddeliveryorders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "DISH_ID")
    private Dish dish;

    @Column(name = "COUNT")
    private int quantity;

    public void addOrder(Order order) {
        this.addOrder(order, false);
    }

    public void addOrder(Order order, boolean hasOrderThisOrderDetail) {
        this.order = order;
        if (hasOrderThisOrderDetail) {
            return;
        }
        order.addOrderDetail(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(dish, that.dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + order +
                ", dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }
}
