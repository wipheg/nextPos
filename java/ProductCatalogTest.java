import org.junit.jupiter.api.Test;
import org.nextGenPos.ItemID;
import org.nextGenPos.Money;
import org.nextGenPos.ProductCatalog;
import org.nextGenPos.ProductSpecification;

import static org.junit.jupiter.api.Assertions.*;

class ProductCatalogTest {
    @Test
    void testAddAndGetPrice() {
        ProductCatalog catalog = new ProductCatalog();
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        catalog.addProductSpecification(itemId, new ProductSpecification(price, "Test Product"));
        assertEquals(100, catalog.getPriceByItemId(itemId).getAmount());
    }

    @Test
    void testGetDescription() {
        ProductCatalog catalog = new ProductCatalog();
        ItemID itemId = new ItemID(1);
        Money price = new Money(100);
        String description = "Test Product";
        catalog.addProductSpecification(itemId, new ProductSpecification(price, description));
        assertEquals(description, catalog.getDescriptionByItemId(itemId));
    }
}
