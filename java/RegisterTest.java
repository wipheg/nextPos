import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.nextGenPos.*;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class RegisterTest {

    private final ProductCatalog productCatalog = new ProductCatalog();
    private final Inventory inventory = new Inventory();


    @Test
    void testSaleProcess() {
        Register register = new Register(productCatalog, inventory);
        register.makeNewSale();
        assertNotNull(register.getSale());
    }

    @Test
    void testEnterItem() {
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        String description = "Test Product";
        productCatalog.addProductSpecification(itemId, new ProductSpecification(price, description));

        Register register = new Register(productCatalog, inventory);
        register.makeNewSale();
        register.enterItem(itemId, 2);

        Sale currentSale = register.getSale();
        Money expectedTotal = new Money(200);
        assertEquals(expectedTotal.getAmount(), currentSale.getTotal().getAmount());
    }

    @Test
    void testEndSale() {
        Register register = new Register(productCatalog, inventory);
        register.makeNewSale();
        register.endSale();

        assertTrue(register.getSale().isComplete());
    }

    @Test
    void testMakeCashPayment() {
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        productCatalog.addProductSpecification(itemId, new ProductSpecification(price, "Test Product"));

        Register register = new Register(productCatalog, inventory);
        register.setPaymentMethod(new CashPayment());
        register.makeNewSale();
        register.enterItem(itemId, 2);
        register.makePayment(new Money(200));

        Sale currentSale = register.getSale();
        assertEquals(200, currentSale.getPayment().getAmount().getAmount());
    }

    @Test
    void testMakeCreditCardPayment() {
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        productCatalog.addProductSpecification(itemId, new ProductSpecification(price, "Test Product"));

        Register register = new Register(productCatalog, inventory);
        register.setPaymentMethod(new CreditCardPayment());
        register.makeNewSale();
        register.enterItem(itemId, 2);
        register.makePayment(new Money(200));

        Sale currentSale = register.getSale();
        assertEquals(200, currentSale.getPayment().getAmount().getAmount());
    }
}
