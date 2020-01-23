package com.supermarketSimulator.controller;

import com.supermarketSimulator.customer.Customer;
import com.supermarketSimulator.customer.CustomerCategory;
import com.supermarketSimulator.product.Discount;
import com.supermarketSimulator.product.Product;
import com.supermarketSimulator.product.ProductType;
import com.supermarketSimulator.supermarket.Supermarket;
import com.supermarketSimulator.functions.Functions;

import java.math.BigDecimal;
import java.util.Date;

public class SupermarketController {
    private final Supermarket supermarket;
    private int countOfCustomers = 0;

    public SupermarketController(int hoursOfWork) {
        supermarket = new Supermarket();
        fillSupermarket();
        Date date = new Date();
        Date closeDate = Functions.addHours(date, hoursOfWork);
        runSupermarket(date, closeDate);
    }

    private void fillSupermarket() {
        supermarket.addProduct(new Product("milk", 0, new BigDecimal(50.0), true, 10, ProductType.BY_COUNT));
        supermarket.addProduct(new Product("bread", 0, new BigDecimal(10.0), true, 20, ProductType.BY_COUNT, new Discount(50)));

        supermarket.showInfoAllProducts();
    }

    private void addCustomerInSupermarket(Date date) {
        int numberOfCategory = Functions.randomNumber(CustomerCategory.values().length);
        Customer customer = new Customer(countOfCustomers, CustomerCategory.values()[numberOfCategory]);
        countOfCustomers++;
    }

    private void customerTookProduct(Date date) {
        int countOfCustomers = supermarket.getCustomers().size();
        int countOfProducts = supermarket.getProducts().size();

        if (countOfCustomers > 0 && countOfProducts > 0) {
            int customerIndex = Functions.randomNumber(countOfCustomers);
            int productIndex = Functions.randomNumber(countOfProducts);

            Integer amountOfGoodsProduct = supermarket.getProduct(productIndex).getAmountOfGoods();
            int amountOfGoodsOfProductMax = amountOfGoodsProduct > 0 ? amountOfGoodsProduct : 1;
            int amountOfGoods = Functions.randomNumber(amountOfGoodsOfProductMax) + 1;

            supermarket.getCustomer(customerIndex).putToBasket(date, amountOfGoods, productIndex);
        }
    }

    private void runSupermarket(Date date, Date dateClose) {
        date = Functions.addRandomTime(date);
        supermarket.openSupermarket(date);
        addCustomerInSupermarket(date);

        customerTookProduct(date);
    }
}
