package org.elvira.fooddeliveryorders.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elvira.fooddeliveryorders.model.Dish;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Dish dish;
    private int quantity;
    private double price;
}
