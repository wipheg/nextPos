import org.junit.jupiter.api.Test;
import org.nextGenPos.Inventory;
import org.nextGenPos.ItemID;
import org.nextGenPos.Money;
import org.nextGenPos.ProductCatalog;
import org.nextGenPos.ProductSpecification;
import org.nextGenPos.Register;
import org.nextGenPos.Sale;
import org.nextGenPos.Store;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BillTest {
    private Store store;
    private Register register;
    private ProductCatalog productCatalog;
    private Inventory inventory;

    private ItemID testItemID1;
    private ItemID testItemID2;
    private ItemID testItemID3;

    private static ByteArrayOutputStream outputMessage;

    @BeforeEach
    void setUp() {
        store = new Store();
        register = store.getRegister();
        productCatalog = store.getCatalog();
        inventory = store.getInventory();

        testItemID1 = new ItemID(1);
        testItemID2 = new ItemID(2);
        testItemID3 = new ItemID(3);
        Money testItemPrice1 = new Money(100);
        Money testItemPrice2 = new Money(300);
        Money testItemPrice3 = new Money(400);
        int initialStockQuantity = 10;

        productCatalog.addProductSpecification(testItemID1, new ProductSpecification(testItemPrice1, "Test Product"));
        productCatalog.addProductSpecification(testItemID2, new ProductSpecification(testItemPrice2, "Test Product"));
        productCatalog.addProductSpecification(testItemID3, new ProductSpecification(testItemPrice3, "Test Product"));
        inventory.addStock(testItemID1, initialStockQuantity);
        inventory.addStock(testItemID2, initialStockQuantity);
        inventory.addStock(testItemID3, initialStockQuantity);

        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));
    }

    @AfterEach
    void restoresStreams() {
        System.setOut(System.out);
    }

    @Test
    void testMakeBill() {
        String consoleResult =
                        "+------------+------------+------------+------------+\n" +
                        "| ItemID     | Unit Cost  | Quantity   | Amount     |\n" +
                        "+------------+------------+------------+------------+\n" +
                        "| 1          | 100        | 5          | 500        |\n" +
                        "| 2          | 300        | 10         | 3000       |\n" +
                        "| 3          | 400        | 3          | 1200       |\n" +
                        "+------------+------------+------------+------------+\n" +
                        "| Total Amount                         | 4700       |\n" +
                        "+------------+------------+------------+------------+\n";

        register.makeNewSale();

        register.enterItem(testItemID1, 5);
        register.enterItem(testItemID2, 10);
        register.enterItem(testItemID3, 3);

        Sale sale = register.getSale();
        sale.makeBill();

        assertEquals(consoleResult, outputMessage.toString());
    }
}
