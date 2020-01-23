package com.supermarketSimulator.product;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final Integer id;
    private boolean legalToChildren;
    private BigDecimal price;
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
        this.discount = new Discount(0);
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

        stringBuffer.append(name).append(" ");
        if (productType == ProductType.BY_COUNT) {
            stringBuffer.append(amountOfGoods).append(" units ").append(price).append("$ for each");
        } else {
            stringBuffer.append(amountOfGoods / 1000).append(" kilogram ").append(price).append("$ for kilogram");
        }
        System.out.println(stringBuffer);
    }

    public void deleteAmountOfGoods(Integer amountOfGoods) {
        if (amountOfGoods <= 0) {
            throw new IllegalArgumentException("The number of products when deleting must be positive");
        }
        this.amountOfGoods -= amountOfGoods;
    }

    public void addAmountOfGoods(Integer amountOfGoods) {
        if (amountOfGoods <= 0) {
            throw new IllegalArgumentException("The number of products when adding must be positive");
        }
        this.amountOfGoods += amountOfGoods;
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

    public String getProductTypeToString() {
        if (productType == ProductType.BY_WEIGHT) {
            return "gram";
        }
        if (productType == ProductType.BY_COUNT) {
            return  "units";
        }
        return "";
    }

    public Discount getDiscount() {
        return discount;
    }

}
