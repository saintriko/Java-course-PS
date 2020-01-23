package com.supermarketSimulator.customer;

import com.supermarketSimulator.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private final List<Product> products;

    public Basket() {
        this.products = new ArrayList<>();
    }

    public void addBasket(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
