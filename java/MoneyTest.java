import org.junit.jupiter.api.Test;
import org.nextGenPos.Money;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void testMoneyAdd() {
        Money money1 = new Money(100);
        Money money2 = new Money(200);
        Money result = money1.add(money2);
        assertEquals(300, result.getAmount());
    }

    @Test
    void testMoneyMinus() {
        Money money1 = new Money(500);
        Money money2 = new Money(200);
        Money result = money1.minus(money2);
        assertEquals(300, result.getAmount());
    }

    @Test
    void testMoneyTimes() {
        Money money = new Money(100);
        Money result = money.times(3);
        assertEquals(300, result.getAmount());
    }


}
