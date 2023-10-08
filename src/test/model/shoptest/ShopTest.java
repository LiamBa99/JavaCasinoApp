package model.shoptest;

import model.prizeshop.*;
import model.casino.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shopTest;
    private Casino casinoTest;

    @BeforeEach
    void runBefore() {
        shopTest = new Shop();
        casinoTest = new Casino(5000);
    }

    @Test
    void testConstructor() {
        assertTrue(shopTest.getPrizeList().size() == 0);
        assertTrue(shopTest.getCurrentCasino() != null);
    }

    @Test
    void testGeneratePrizes() {
        // base case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());
        assertFalse(shopTest.getPrizeList().get(0) == shopTest.getPrizeList().get(1));

        // min case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());

        // large num case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(25);
        assertEquals(25,shopTest.getPrizeList().size());
        assertFalse(shopTest.getPrizeList().get(5) == shopTest.getPrizeList().get(15));
    }

    @Test
    void testRefreshPrizes() {
        // base case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());
        shopTest.refreshPrizes(8);
        assertEquals(8,shopTest.getPrizeList().size());

        // min case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());
        shopTest.refreshPrizes(0);
        assertEquals(0,shopTest.getPrizeList().size());

        // large num case
        assertEquals(0,shopTest.getPrizeList().size());
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());
        shopTest.refreshPrizes(25);
        assertEquals(25,shopTest.getPrizeList().size());
    }

    @Test
    void testBuyPrize() {
        int oldBalance = casinoTest.getPlayerBalance();
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());

        Prize purchasedPrize = shopTest.buyPrize(4);
        assertEquals(4,shopTest.getPrizeList().size());

        assertEquals(purchasedPrize,shopTest.getCurrentCasino().getPrizeList().get(0));

        assertEquals(casinoTest.getPlayerBalance(),oldBalance - purchasedPrize.getValue());
    }

    @Test
    void testSellPrize() {
        int oldBalance = casinoTest.getPlayerBalance();
        shopTest.generatePrizes(5);
        assertEquals(5,shopTest.getPrizeList().size());

        Prize purchasedPrize = shopTest.buyPrize(4);
        assertEquals(4,shopTest.getPrizeList().size());

        assertEquals(purchasedPrize,shopTest.getCurrentCasino().getPrizeList().get(0));

        assertEquals(casinoTest.getPlayerBalance(),oldBalance - purchasedPrize.getValue());

        assertTrue(shopTest.sellPrize(purchasedPrize));

        assertEquals(oldBalance, casinoTest.getPlayerBalance());
        assertFalse(casinoTest.getPrizeList().contains(purchasedPrize));
    }
}
