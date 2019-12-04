package SupermarketPackage;

public class Supermarket {


    public static void main (String[] arg) {
        Customer customer1 = new Customer("Customer#1", Customer.Category.Adult);
        System.out.println(customer1.GetBasket());

    }
}