package org.elvira.fooddeliveryorders.services.interfaces;

import org.elvira.fooddeliveryorders.model.Order;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrder(Long orderId);
    Order updateOrder(Order order, Long orderId);
    void deleteOrder(Long orderId);
}
