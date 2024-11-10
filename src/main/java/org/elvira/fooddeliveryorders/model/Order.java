package org.elvira.fooddeliveryorders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    public void addOrderDetail(OrderDetail orderDetail) {
        this.addOrderDetail(orderDetail, false);
    }

    public void addOrderDetail(OrderDetail orderDetail, boolean hasOrderDetailThisOrder) {
        this.getOrderDetails().add(orderDetail);
        if (hasOrderDetailThisOrder) {
            return;
        }
        orderDetail.addOrder(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
