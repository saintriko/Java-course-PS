package com.supermarketSimulator.supermarket;

import com.supermarketSimulator.customer.Customer;
import com.supermarketSimulator.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supermarket {
    private final List<Customer> customers;
    private final List<Product> products;
//    private final CashDesk cashDesk;
//    private final Report report;

    private boolean isOpen;

    public Supermarket() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
//        report = new Report();
//        cashDesk = new CashDesk(report);
        isOpen = false;
    }

    public void openSupermarket(Date date) {
        if (!isOpen) {
            isOpen = true;
            System.out.println(date + " We are open!");
        }
    }

    public void closeSupermarket(Date date) {
        if (isOpen) {
            isOpen = false;
            System.out.println(date + "We are close.");
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public Customer getCustomer(int index) {
        return customers.get(index);
    }

    public void showInfoAllProducts() {
        for (Product product : products) {
            product.showInfo();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
