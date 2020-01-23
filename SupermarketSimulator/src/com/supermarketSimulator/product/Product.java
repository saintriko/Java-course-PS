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

    public boolean isLegalToChildren() {
        return this.legalToChildren;
    }
    public void showInfo() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(name);
        System.out.println(stringBuffer);
    }

    public void deleteAmountOfGoods(Integer quantityGoods) {
        if (quantityGoods <= 0) {
            throw new IllegalArgumentException("The number of products when deleting must be positive");
        }
        this.amountOfGoods -= quantityGoods;
    }

    public Integer getAmountOfGoods() {
        return amountOfGoods;
    }
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Discount getDiscount() {
        return discount;
    }

}
