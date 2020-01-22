package com.supermarketSimulator.product;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final Integer id;
    private BigDecimal price;
    private boolean legalToChildren;
    private Discount discount;
    private Integer amountOfGoods;
    private final ProductType productType;

    public Product(String name, Integer id, BigDecimal price, boolean legalToChildren, Integer amountOfGoods, ProductType productType) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.legalToChildren = legalToChildren;
        this.amountOfGoods = amountOfGoods;
        this.productType = productType;
    }

    public Product(String name, Integer id, BigDecimal price, boolean legalToChildren, Integer amountOfGoods, ProductType productType, Discount discount) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.legalToChildren = legalToChildren;
        this.amountOfGoods = amountOfGoods;
        this.productType = productType;
        this.discount = discount;
    }

    public void showInfo() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        System.out.println(stringBuffer);
    }

    public Integer getAmountOfGoods() {
        return amountOfGoods;
    }

}
