package com.supermarketSimulator.customer;

import com.supermarketSimulator.product.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Customer {
    private Basket basket;
    public CustomerCategory customerCategory;
    private final int customerNumber;
    private final List<Product> products;
    private static Integer allCustomerNumber = 0;
    private BigDecimal cash;
    private BigDecimal bankCard;
    private BigDecimal bonus;
    private BigDecimal restMoney;
    private boolean isDiscount;

    public Customer ( int number, CustomerCategory category) {
        this.customerCategory = category;
        this.customerNumber = number;
    }

    public void putToBasket(Date date, Integer amountOfGoods, int index) {
        Product product = getProduct(index);
        BigDecimal productPrice = getProductPriceToCheck(product, amountOfGoods);
        restMoney = restMoney.subtract(productPrice);
        product.deleteAmountOfGoods(amountOfGoods);
        Product productToBasket = new Product(product.getName(), product.getId(), product.getPrice(), product.isLegalToChildren(), amountOfGoods, product.getProductType(), product.getDiscount());
        basket.addBasket(productToBasket);
        printInfoAddProductToBasket(product, amountOfGoods, date);
    }

    private BigDecimal getProductPriceToCheck(Product product, Integer amountOfGoods) {
        if (customerCategory == CustomerCategory.Child && product.isLegalToChildren()) {
            throw new IllegalArgumentException("Customer '" + getName() + "' picked up '" + product.getName() + "' which is not for children");
        }
        if (product.getAmountOfGoods() < amountOfGoods) {
            throw new IllegalArgumentException("Customer '" + getName() + "' picked up '" + product.getName() + "' more products than there are in the supermarket");
        }
        BigDecimal price = getTypeProductSalePrice(product, amountOfGoods, product.getPrice());
        price = getDiscountPrice(product, price);
        if (restMoney.compareTo(price) < 0) {
            throw new IllegalArgumentException("Customer '" + getName() + "' not enough money for the product");
        }
        return price;
    }


    private Product getProduct(int index) {
        return products.get(index);
    }

    public Basket GetBasket() {
        return basket;
    }

    private Integer getCustomerNumber() {
        return customerNumber;
    }
    public String getName() {
        return "customer#" + getCustomerNumber();
    }
}
