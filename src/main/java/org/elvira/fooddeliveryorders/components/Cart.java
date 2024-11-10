package org.elvira.fooddeliveryorders.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elvira.fooddeliveryorders.model.Dish;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;

@Getter
@Component
@SessionScope
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Set<CartItem> items = new HashSet<>();

    public void addItem(Dish dish, int quantity) {
        for (CartItem item : items) {
            Dish findedDish = item.getDish();
            if (findedDish.getId().equals(dish.getId())) {
                int newQuantity = item.getQuantity() + quantity;
                item.setQuantity(newQuantity);
                item.setPrice(round(newQuantity * findedDish.getPrice()));
                return;
            }
        }
        items.add(new CartItem(dish, quantity, round(quantity * dish.getPrice())));
    }

    public void updateItemQuantity(Long dishId, int quantity) {
        for (CartItem item : items) {
            Dish dish = item.getDish();
            if (dish.getId().equals(dishId)) {
                item.setQuantity(quantity);
                item.setPrice(round(quantity * dish.getPrice()));
                return;
            }
        }
    }

    public void removeItem(Long dishId) {
        items.removeIf(item -> item.getDish().getId().equals(dishId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotalPrice() {
        double sum = items.stream().mapToDouble(item -> item.getDish().getPrice() * item.getQuantity()).sum();

        return round(sum);
    }

    private static double round(double sum) {
        double pow = pow(10, 2);
        return Math.round(sum * pow) / pow;
    }
}
