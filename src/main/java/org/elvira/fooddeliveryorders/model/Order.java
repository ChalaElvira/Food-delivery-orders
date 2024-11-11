package org.elvira.fooddeliveryorders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
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
    private Long id;

    @Column(name = "DATE")
    private Date date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TOTAL")
    private Double total;

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
        return Objects.equals(date, order.date)
                && Objects.equals(orderDetails, order.orderDetails)
                && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, orderDetails, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", orderDetails=" + orderDetails +
                ", user=" + user +
                '}';
    }
}
