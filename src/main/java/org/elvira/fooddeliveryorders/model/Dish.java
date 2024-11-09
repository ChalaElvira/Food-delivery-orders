package org.elvira.fooddeliveryorders.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "DISHES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private DishType dishType;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
    private Restaurant restaurant;

    public void setRestaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant, false);
    }

    public void setRestaurant(Restaurant restaurant, boolean hasRestaurantThisDish) {
        this.restaurant = restaurant;
        if (hasRestaurantThisDish) {
            return;
        }
        restaurant.addDish(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description) && Objects.equals(price, dish.price) && dishType == dish.dishType && Objects.equals(restaurant, dish.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, dishType);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dishType=" + dishType +
                ", restaurant=" + restaurant +
                '}';
    }
}
