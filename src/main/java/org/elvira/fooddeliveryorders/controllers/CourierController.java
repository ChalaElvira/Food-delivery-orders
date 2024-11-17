package org.elvira.fooddeliveryorders.controllers;

import org.elvira.fooddeliveryorders.model.Order;
import org.elvira.fooddeliveryorders.model.OrderStatus;
import org.elvira.fooddeliveryorders.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courier/{courierId}")
public class CourierController {
    private static final String COURIER_ID = "courierId";

    private final IOrderService orderService;

    @Autowired
    public CourierController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute
    public void addAttributes(@PathVariable(COURIER_ID) Long courierId,
                              Model model) {
        model.addAttribute(COURIER_ID, courierId);
    }

    // Сторінка зі списком замовлень зі статусом "Готове до видачі"
    @GetMapping("/orders-ready")
    public String viewReadyOrders(Model model) {
        List<Order> readyOrders = orderService.getOrdersByStatus(OrderStatus.READY);
        model.addAttribute("ordersReady", readyOrders);
        List<Order> ordersTransit = orderService.getOrdersByStatus(OrderStatus.IN_TRANSIT);
        model.addAttribute("ordersTransit", ordersTransit);
        return "courier/orders-ready";
    }

    // Прийняття замовлення кур'єром
    @PostMapping("/accept-order/{orderId}")
    public String acceptOrder(@PathVariable Long orderId,
                              Model model) {
        Order order = orderService.getOrder(orderId);
        if (order.getStatus() == OrderStatus.READY) {
            order.setStatus(OrderStatus.IN_TRANSIT);
            orderService.updateOrder(order, orderId);
        }
        return "redirect:/courier/%s/orders-ready".formatted(model.getAttribute(COURIER_ID));
    }

    // Завершення замовлення (Виконано або Скасовано)
    @PostMapping("/complete-order/{orderId}")
    public String completeOrder(@PathVariable Long orderId,
                                @RequestParam("status") String status,
                                Model model) {
        Order order = orderService.getOrder(orderId);

        if (order.getStatus() == OrderStatus.IN_TRANSIT) {
            switch (status) {
                case "COMPLETED" -> order.setStatus(OrderStatus.COMPLETED);
                case "CANCELLED" -> order.setStatus(OrderStatus.CANCELLED);
                default -> throw new IllegalArgumentException("Невідомий статус: " + status);
            }
            orderService.updateOrder(order, orderId);
        }

        return "redirect:/courier/%s/orders-ready".formatted(model.getAttribute(COURIER_ID));
    }
}
