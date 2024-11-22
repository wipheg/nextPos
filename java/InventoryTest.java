import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nextGenPos.Inventory;
import org.nextGenPos.ItemID;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private ItemID testItemID;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        testItemID = new ItemID(1);
    }

    @Test
    void testAddAndRemoveStock() {
        inventory.addStock(testItemID, 10);
        assertEquals(10, inventory.getStockQuantity(testItemID));

        inventory.removeStock(testItemID, 5);
        assertEquals(5, inventory.getStockQuantity(testItemID));
    }

    @Test
    void testRemoveStockThrowsExceptionForInsufficientStock() {
        inventory.addStock(testItemID, 2);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.removeStock(testItemID, 5);
        });

        String expectedMessage = "Insufficient stock for ItemID " + testItemID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetStockQuantityForNonExistentItem() {
        assertEquals(0, inventory.getStockQuantity(new ItemID(999)));
    }
}
