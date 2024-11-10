package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Order;
import org.elvira.fooddeliveryorders.repositories.OrderRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Order updateOrder(Order order, Long orderId) {
        Order orderToUpdate = orderRepository.findById(orderId).orElseThrow();

        orderToUpdate.getOrderDetails().clear();
        order.getOrderDetails().forEach(orderToUpdate::addOrderDetail);

        return orderRepository.save(orderToUpdate);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
