package org.elvira.fooddeliveryorders.components;

import org.elvira.fooddeliveryorders.model.OrderStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToOrderStatusConverter implements Converter<String, OrderStatus> {
    @Override
    public OrderStatus convert(@NonNull String source) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.toString().equalsIgnoreCase(source)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Невідомий статус: " + source);
    }
}
