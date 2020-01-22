package com.supermarketSimulator.customer;

public class Customer {
    private Basket basket;
    public CustomerCategory category;
    private final int number;

    public Basket GetBasket() {
        return basket;
    }

    public Customer ( int number, CustomerCategory category) {
        this.category = category;
        this.number = number;
    }
}
