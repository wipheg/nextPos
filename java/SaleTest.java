import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.*;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    private Store store;
    private Sale sale;
    private PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        sale = new Sale();
        paymentMethod = new CashPayment();
        store = new Store();
    }

    @Test
    void testAddLineItemAndTotal() {
        ProductCatalog catalog = new ProductCatalog();
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        catalog.addProductSpecification(itemId, new ProductSpecification(price, "Test Product"));
        sale.makeLineItem(itemId, price, 2);
        assertEquals(200, sale.getTotal().getAmount());
    }

    @Test
    void testPayment() {
        Money cashTendered = new Money(500);
        sale.makePayment(cashTendered, paymentMethod);
        assertEquals(500, sale.getPayment().getAmount().getAmount());
    }

    @Test
    void testGetBalance() {
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        int quantity = 2;
        sale.makeLineItem(itemId, price, quantity);
        Money cashTendered = new Money(250);
        sale.makePayment(cashTendered, paymentMethod);

        int expectedBalance = cashTendered.getAmount() - price.getAmount() * quantity;

        assertEquals(expectedBalance, sale.getBalance().getAmount());
    }

    @Test
    void savePointTest(){
        CustomerID customerID = new CustomerID(123);
        store.makeCustomer(customerID);

        sale.setCustomers(store.getCustomers());
        
        sale.savePoint(customerID,new Point(500));

        assertEquals(500, sale.getCustomers().getPointByCustomerId(customerID).getPoint());
    }

    @Test
    void usePointTest(){
        CustomerID customerID = new CustomerID(123);
        store.makeCustomer(customerID);

        sale.setCustomers(store.getCustomers());
        
        sale.savePoint(customerID,new Point(500));

        sale.usePoint(customerID,new Point(200));

        assertEquals(300, sale.getCustomers().getPointByCustomerId(customerID).getPoint());
    }
}
