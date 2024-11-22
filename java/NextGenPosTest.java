import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.*;

import static org.junit.jupiter.api.Assertions.*;

class NextGenPosTest {

    private Store store;
    private ProductCatalog productCatalog;
    private Inventory inventory;
    private Register register;
    private ItemID testItemID;
    private Money testItemPrice;
    private int initialStockQuantity;

    @BeforeEach
    void setUp() {
        store = new Store();
        productCatalog = store.getCatalog();
        inventory = store.getInventory();
        register = store.getRegister();
        testItemID = new ItemID(1);
        testItemPrice = new Money(100);
        initialStockQuantity = 10;

        productCatalog.addProductSpecification(testItemID, new ProductSpecification(testItemPrice, "Test Product"));
        inventory.addStock(testItemID, initialStockQuantity);
    }

    @Test
    void testCompleteSaleProcess() {
        register.makeNewSale();

        int quantitySold = 3;
        register.enterItem(testItemID, quantitySold);

        register.setPaymentMethod(new CashPayment());
        register.makePayment(new Money(300));
        register.endSale();

        int expectedRemainingStock = initialStockQuantity - quantitySold;
        assertEquals(expectedRemainingStock, inventory.getStockQuantity(testItemID));

        assertTrue(register.getSale().isComplete());


        Money expectedTotal = testItemPrice.times(quantitySold);
        assertEquals(expectedTotal.getAmount(), register.getSale().getTotal().getAmount());
    }

    @Test
    void testSaleWithInsufficientPayment() {
        register.makeNewSale();

        register.enterItem(testItemID, 2);

        register.setPaymentMethod(new CashPayment());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            register.makePayment(new Money(150));
        });

        assertFalse(register.getSale().isComplete());

        assertEquals(initialStockQuantity, inventory.getStockQuantity(testItemID));
    }

    @Test
    void testMultipleItemsSale() {
        ItemID secondItemID = new ItemID(2);
        Money secondItemPrice = new Money(200);
        int secondItemStockQuantity = 5;
        productCatalog.addProductSpecification(secondItemID, new ProductSpecification(secondItemPrice, "Second Test Product"));
        inventory.addStock(secondItemID, secondItemStockQuantity);

        register.makeNewSale();
        register.enterItem(testItemID, 1);
        register.enterItem(secondItemID, 2);

        register.setPaymentMethod(new CashPayment());
        register.makePayment(new Money(500));
        register.endSale();

        assertTrue(register.getSale().isComplete());
        assertEquals(initialStockQuantity - 1, inventory.getStockQuantity(testItemID));
        assertEquals(secondItemStockQuantity - 2, inventory.getStockQuantity(secondItemID));

        Money expectedTotal = testItemPrice.times(1).add(secondItemPrice.times(2));
        assertEquals(expectedTotal.getAmount(), register.getSale().getTotal().getAmount());
    }

}
