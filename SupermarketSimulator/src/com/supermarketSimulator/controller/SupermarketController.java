package com.supermarketSimulator.controller;

import com.supermarketSimulator.customer.Customer;
import com.supermarketSimulator.customer.CustomerCategory;
import com.supermarketSimulator.product.Discount;
import com.supermarketSimulator.product.Product;
import com.supermarketSimulator.product.ProductType;
import com.supermarketSimulator.supermarket.CashDesk;
import com.supermarketSimulator.supermarket.Supermarket;
import com.supermarketSimulator.functions.Functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private void newCustomerInSupermarket(Date date) {
        int category = Functions.randomNumber(CustomerCategory.values().length);
        BigDecimal cash = giveMoneyRandom(new BigDecimal(0), new BigDecimal(2000));
        BigDecimal bankCard = giveMoneyRandom(new BigDecimal(0), new BigDecimal(3000));
        BigDecimal bonus = giveMoneyRandom(new BigDecimal(0), new BigDecimal(100));

        Customer customer = new Customer(countOfCustomers, CustomerCategory.values()[category], cash, bankCard, bonus, supermarket.getProducts());
        countOfCustomers++;
        supermarket.addCustomerToSupermarket(customer);
        supermarket.InfoAboutCustomer(date, customer);
    }

    private void fillSupermarket() {
        supermarket.addProduct(new Product("milk", 0, new BigDecimal(50.0), true, 100, ProductType.BY_COUNT));
        supermarket.addProduct(new Product("bread", 1, new BigDecimal(10.0), true, 200, ProductType.BY_COUNT, new Discount(50)));
        supermarket.addProduct(new Product("banana", 2, new BigDecimal(10.0), true, 1000, ProductType.BY_WEIGHT));
        supermarket.addProduct(new Product("bottle of whiskey", 3, new BigDecimal(100.0), false, 10, ProductType.BY_COUNT));
        supermarket.addProduct(new Product("bottle of water", 4, new BigDecimal(10.0), true, 10, ProductType.BY_COUNT));

        supermarket.listOfProducts();
    }

    private void customerToCashDesk() {
        int customerSize = supermarket.getCustomers().size();

        if (customerSize > 0) {
            int customerIndex = Functions.randomNumber(customerSize);
            Customer customer = supermarket.getCustomer(customerIndex);
            if (customer.getBasketProduct().size() > 0) {
                supermarket.getCashDesk().addCustomerToCashDesk(customer);
            }
        }
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

    private void serviceCustomer(Date date) {
        CashDesk cashDesk = supermarket.getCashDesk();
        if (cashDesk.getCustomers().size() > 0) {
            Customer customer = cashDesk.getCustomer();
            customer.paymentBill(cashDesk.serviceCustomer(date), date);
            cashDesk.deleteCustomerToCashDesk();
            supermarket.deleteCustomer(customer);
        }
    }

    private void runSupermarket(Date date, Date dateClose) {
        date = Functions.addRandomTime(date);
        supermarket.openSupermarket(date);
        newCustomerInSupermarket(date);


        while (supermarket.getCustomers().size() > 0 || date.getTime() < dateClose.getTime()) {
            try {
                int randomNumber = Functions.randomNumber(100);
                date = Functions.addRandomTime(date);

                if (date.getTime() >= dateClose.getTime()) {
                    supermarket.deleteAllCustomer();
                    break;
                }
                customerTookProduct(date);
                if (randomNumber > 0 && randomNumber < 40) {
                    serviceCustomer(date);
                } else if (randomNumber >= 40 && randomNumber < 65) {
                    customerToCashDesk();
                } else if (randomNumber >= 65 && randomNumber <= 80) {
                    newCustomerInSupermarket(date);
                } else {
                    customerTookProduct(date);
                }

            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
            }
        }

        supermarket.closeSupermarket(date);
    }

    private BigDecimal giveMoneyRandom(BigDecimal minTotal, BigDecimal maxTotal) {
        BigDecimal randomBigDecimal = minTotal.add(new BigDecimal(Math.random()).multiply(maxTotal.subtract(minTotal)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

}
