package org.elvira.fooddeliveryorders.model;

public enum OrderStatus {
    /**
     * Створено.
     */
    PENDING,       // Замовлення створено, очікує підтвердження

    /**
     * Підтвердено.
     */
    CONFIRMED,     // Замовлення підтверджено

    /**
     * Готується.
     */
    PREPARING,     // Замовлення готується

    /**
     * Готове до видачі.
     */
    READY,         // Замовлення готове до доставки/забору

    /**
     * Доставляєтсья.
     */
    IN_TRANSIT,    // Замовлення доставляється

    /**
     * Виконано.
     */
    COMPLETED,     // Замовлення виконано

    /**
     * Скасовано.
     */
    CANCELLED;     // Замовлення скасовано

    @Override
    public String toString() {
        return switch (this) {
            case PENDING -> "Створено";
            case CONFIRMED -> "Підтверджено";
            case PREPARING -> "Готується";
            case READY -> "Готове до видачі";
            case IN_TRANSIT -> "Доставляється";
            case COMPLETED -> "Виконано";
            case CANCELLED -> "Скасовано";
        };
    }
}