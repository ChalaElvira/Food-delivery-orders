package org.elvira.fooddeliveryorders.services;

import org.elvira.fooddeliveryorders.model.Order;
import org.elvira.fooddeliveryorders.model.User;
import org.elvira.fooddeliveryorders.repositories.OrderRepository;
import org.elvira.fooddeliveryorders.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
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

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return orderRepository.findOrderByUser(user);
    }
}
