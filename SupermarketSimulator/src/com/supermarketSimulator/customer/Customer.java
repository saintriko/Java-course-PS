package com.supermarketSimulator.customer;

import com.supermarketSimulator.functions.Functions;
import com.supermarketSimulator.product.Product;
import com.supermarketSimulator.product.ProductType;
import com.supermarketSimulator.supermarket.Bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Customer {
    private Basket basket;
    public CustomerCategory customerCategory;
    private final int customerNumber;
    private final List<Product> products;
    private BigDecimal cash;
    private BigDecimal bankCard;
    private BigDecimal bonus;
    private BigDecimal restMoney;
    private boolean isDiscount;

    public Customer (int number, CustomerCategory customerCategory, BigDecimal cash, BigDecimal bankCard, BigDecimal bonus, List<Product> products) {
        this.customerNumber = number;
        this.customerCategory = customerCategory;
        this.cash = cash;
        this.bankCard = bankCard;
        this.bonus = bonus;
        this.products = products;
        this.restMoney = getAllMoney();
        basket = new Basket();
        isDiscount = false;
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
        if (customerCategory == CustomerCategory.Child && !product.isLegalToChildren()) {
            throw new IllegalArgumentException(getName() + " is a kid, can't take " + product.getName());
        }
        if (product.getAmountOfGoods() < amountOfGoods) {
            throw new IllegalArgumentException("There no more " + product.getName() + " in supermarket for " + getName() );
        }
        BigDecimal price = getTypeProductSalePrice(product, amountOfGoods, product.getPrice());
        price = getDiscountPrice(product, price);
        if (restMoney.compareTo(price) < 0) {
            throw new IllegalArgumentException(getName() + " don't have enough money for the product ");
        }
        return price;
    }

    private void printInfoAddProductToBasket(Product product, Integer amountOfGoods, Date date) {
        String str = Functions.getTime(date) + getName() + " took "
                + amountOfGoods + " " + product.getProductTypeToString() + " of '" + product.getName() + "'";
        System.out.println(str);
    }

    private BigDecimal getTypeProductSalePrice(Product product, Integer amountOfGoods, BigDecimal price) {
        if (product.getProductType() == ProductType.BY_COUNT) {
            return price.multiply(new BigDecimal(amountOfGoods));
        } else if (product.getProductType() == ProductType.BY_WEIGHT) {
            return price.multiply(new BigDecimal(amountOfGoods / 1000));
        }
        return price;
    }

    private BigDecimal getDiscountPrice(Product product, BigDecimal price) {
        Integer value = product.getDiscount().getValue();
        if (customerCategory == CustomerCategory.Retired && value != 0) {
            isDiscount = true;
            return price.multiply(new BigDecimal(1 - value / 100));
        } else {
            return price;
        }
    }

    public void paymentBill(Bill bill, Date date) {
        BigDecimal residue = bill.getPrice();

        if (residue.signum() <= 0) {
            throw new IllegalArgumentException(getName() + " don't have enough money for the product");
        }

        StringBuilder stringBuilder = new StringBuilder();
        printInfoPaymentBill(stringBuilder, date);

        if (bonus.signum() > 0) {
            BigDecimal bonusPayment = getPricePayment(residue, bonus);
            setBonus(bonus.subtract(bonusPayment));
            residue = residue.subtract(bonusPayment);
            stringBuilder.append(" ").append(bonusPayment).append(" by ").append(PaymentMethod.BONUS);
        }
        if (bankCard.signum() > 0 && residue.signum() > 0) {
            BigDecimal bankCardPayment = getPricePayment(residue, bankCard);
            setBankCard(bankCard.subtract(bankCardPayment));
            residue = residue.subtract(bankCardPayment);
            stringBuilder.append("; ").append(bankCardPayment).append(" by ").append(PaymentMethod.BANK_CARD);
        }
        if (residue.signum() > 0) {
            setCash(cash.subtract(residue));
            stringBuilder.append("; ").append(residue).append(" by ").append(PaymentMethod.CASH);
        }

        System.out.println(stringBuilder);
    }

    private void printInfoPaymentBill(StringBuilder stringBuilder, Date date) {
        stringBuilder.append(Functions.getTime(date)).append(" '").append(getName()).append("' ");
        if (isDiscount) {
            stringBuilder.append("with discount, ");
        }
        stringBuilder.append("paid");
    }

    private BigDecimal getPricePayment(BigDecimal residue, BigDecimal money) {
        return residue.compareTo(money) < 0 ? residue : money;
    }

    private Product getProduct(int index) {
        return products.get(index);
    }

    public String getName() {
        return "customer#" + getCustomerNumber();
    }
    public BigDecimal getPriceCashDesk() {
        return this.getAllMoney().subtract(restMoney);
    }

    private BigDecimal getAllMoney() {
        return cash.add(bankCard).add(bonus);
    }

    private Integer getCustomerNumber() {
        return customerNumber;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public BigDecimal getBankCard() {
        return bankCard;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public Basket getBasket() {
        return basket;
    }

    public CustomerCategory getCustomerCategory() {
        return customerCategory;
    }

    public List<Product> getBasketProduct() {
        return basket.getProducts();
    }

    private void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    private void setBankCard(BigDecimal bankCard) {
        this.bankCard = bankCard;
    }

    private void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
