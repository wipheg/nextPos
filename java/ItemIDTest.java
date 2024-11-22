import org.junit.jupiter.api.Test;
import org.nextGenPos.ItemID;

import static org.junit.jupiter.api.Assertions.*;

class ItemIDTest {
    @Test
    void testItemIDCreation() {
        ItemID itemId = new ItemID(123);
        assertEquals(123, itemId.getItemId());
    }
}
