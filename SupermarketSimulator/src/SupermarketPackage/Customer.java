package SupermarketPackage;

public class Customer {
    private Basket m_basket;
    public Category m_category;

    public enum Category {
        Adult,
        Child,
        Retired
    }

    String m_name;

    public Basket GetBasket() {
        return m_basket;
    }

    public Customer (String name, Category category) {
        this.m_name = name;
        this.m_category = category;
    }
}
