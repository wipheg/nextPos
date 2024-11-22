import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private PaymentMethod paymentMethod;

    @BeforeEach
    void setUp() {
        paymentMethod = new CashPayment();
    }

    @Test
    void testPaymentCreation() {
        Money cashTendered = new Money(500);
        Payment payment = new Payment(cashTendered, paymentMethod);
        assertEquals(500, payment.getAmount().getAmount());
    }
}
