import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.*;

import static org.junit.jupiter.api.Assertions.*;

class SaleInventoryUpdateTest {

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
    void testInventoryUpdateAfterSale() {
        register.makeNewSale();

        int quantitySold = 3;
        register.enterItem(testItemID, quantitySold);

        register.setPaymentMethod(new CashPayment());
        register.makePayment(new Money(300));
        register.endSale();

        int expectedRemainingStock = initialStockQuantity - quantitySold;
        assertEquals(expectedRemainingStock, inventory.getStockQuantity(testItemID));
    }
}
