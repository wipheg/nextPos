import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.Customers;
import org.nextGenPos.CustomerID;
import org.nextGenPos.Point;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customers customers;
    private CustomerID customerID;

    @BeforeEach
    void setUp() {
        customers = new Customers();
        customerID = new CustomerID(123);
    }

    @Test
    void testAddCustomer() {
        customers.addCustomer(customerID);
        assertNotNull(customers.getPointByCustomerId(customerID));
    }

    @Test
    void testGetPointByCustomerId() {
        customers.addCustomer(customerID);
        assertEquals(0, customers.getPointByCustomerId(customerID).getPoint());
    }

    @Test
    void testAddPointByCustomerId() {
        customers.addCustomer(customerID);
        
        Point point = new Point(100);
        customers.addPointByCustomerId(customerID,point);

        assertEquals(100, customers.getPointByCustomerId(customerID).getPoint());
    }

    @Test
    void testMinusPointByCustomerId() {
        customers.addCustomer(customerID);

        Point point1 = new Point(500);
        customers.addPointByCustomerId(customerID,point1);

        Point point2 = new Point(300);
        customers.minusPointByCustomerId(customerID,point2);

        assertEquals(200, customers.getPointByCustomerId(customerID).getPoint());
    }
}
