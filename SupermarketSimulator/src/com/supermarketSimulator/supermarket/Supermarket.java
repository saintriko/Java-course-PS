package com.supermarketSimulator.supermarket;

import com.supermarketSimulator.customer.Customer;
import com.supermarketSimulator.functions.Functions;
import com.supermarketSimulator.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supermarket {
    private final List<Customer> customers;
    private final List<Product> products;
    private final CashDesk cashDesk;
    private final Report report;
    private boolean isOpen;

    public Supermarket() {
        products = new ArrayList<>();
        customers = new ArrayList<>();
        report = new Report();
        cashDesk = new CashDesk(report);
        isOpen = false;
    }

    public void openSupermarket(Date date) {
        if (!isOpen) {
            isOpen = true;
            System.out.println();
            System.out.println(Functions.getTime(date) + " WE ARE OPEN!");
        }
    }

    public void closeSupermarket(Date date) {
        if (isOpen) {
            isOpen = false;
            System.out.println(Functions.getTime(date) + "WE ARE CLOSED.");
            report.printDailyInfo();
        }
    }

    public void addCustomerToSupermarket(Customer customer) {
        customers.add(customer);
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

    public void listOfProducts() {
        System.out.println("PRODUCT LIST:");
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

    public void deleteCustomer(Customer customer) {
        if (customers.isEmpty()) {
            throw new IllegalArgumentException("No customers for removing from supermarket");
        }
        customers.remove(customer);
    }

    public void deleteAllCustomer() {
        if (customers.isEmpty()) {
            throw new IllegalArgumentException("No customers for full cleaning from supermarket");
        }
        clearBasketCustomer();
        customers.clear();
    }

    public void InfoAboutCustomer(Date date, Customer customer) {
        String string = Functions.getTime(date) + "New customer '" + customer.getName() + "' arrived; category - " +
                customer.getCustomerCategory().name() + "; cash - " + customer.getCash() + "; bank card - " + customer.getBankCard() + "; bonus - " + customer.getBonus();
        System.out.println(string);
    }

    private void clearBasketCustomer() {
        for (Customer customer: customers) {
            for (Product product: customer.getBasketProduct()) {
                Product findProduct = getProduct(product.getId());
                findProduct.addAmountOfGoods(product.getAmountOfGoods());
            }
        }
    }

    public CashDesk getCashDesk() {
        return cashDesk;
    }
}
