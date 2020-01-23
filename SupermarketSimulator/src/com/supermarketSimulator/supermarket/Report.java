package com.supermarketSimulator.supermarket;

import com.supermarketSimulator.customer.Basket;
import com.supermarketSimulator.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Report {
    private final Map<Basket, BigDecimal> basketBigDecimalMap;

    public Report() {
        basketBigDecimalMap = new HashMap<>();
    }

    public void addBasketReport(Basket basket, BigDecimal price) {
        basketBigDecimalMap.put(basket, price);
    }

    public void printDailyInfo() {
        System.out.println();
        System.out.println(basketBigDecimalMap.isEmpty() ? "Nothing was sold in a day" : "REPORT: ");

        for (Map.Entry<Basket, BigDecimal> entry : basketBigDecimalMap.entrySet()) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (Product product: entry.getKey().getProducts()) {
                stringJoiner.add(product.getName() + ": " +  product.getAmountOfGoods() + " " + product.getProductTypeToString());
            }
            System.out.println("[" + stringJoiner + "]: " + entry.getValue());
        }
    }
}
