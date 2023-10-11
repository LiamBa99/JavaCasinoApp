package model.shoptest;

import model.prizeshop.*;
import model.casino.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ShopTest {

    private Shop shopTest;
    private Casino casinoTest;
    private final List<String> animalTypeList = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        casinoTest = new Casino(10000);
        shopTest = new Shop(casinoTest);
        animalTypeList.add("Elephant");
        animalTypeList.add("Giraffe");
        animalTypeList.add("Rhino");
        animalTypeList.add("T-Rex");
        animalTypeList.add("Dog");
        animalTypeList.add("Cat");
        animalTypeList.add("Hamster");
        animalTypeList.add("Gerbil");
        animalTypeList.add("Raccoon");
    }

    @Test
    void testConstructor() {
        assertNotNull(shopTest.getCurrentCasino());
    }

    @Test
    void testGeneratePrizes() {
        // base case
        assertEquals(9,shopTest.getPrizeList().size());
        assertNotSame(shopTest.getPrizeList().get(0), shopTest.getPrizeList().get(1));

        List<Prize> testPrizeList = shopTest.getPrizeList();
        Prize testPrize = testPrizeList.get(0);
        assertTrue(testPrize.getValue() > 0 && testPrize.getValue() <= 10000);
        int colourSum = testPrize.getColour()[0] + testPrize.getColour()[1] + testPrize.getColour()[2];
        assertTrue(colourSum > 0 && colourSum < 765);
        assertTrue(animalTypeList.contains(testPrize.getAnimalType()));
    }


    @Test
    void testBuyPrize() {
        int oldBalance = casinoTest.getPlayerBalance();
        shopTest.generatePrizes();
        assertEquals(9,shopTest.getPrizeList().size());

        casinoTest.deductPlayerBalance(10000);
        assertFalse(shopTest.buyPrize(4));
        
        casinoTest.addPlayerBalance(10000);
        assertTrue(shopTest.buyPrize(4));
        Prize purchasedPrize = casinoTest.getInventory().get(0);
        assertEquals(8,shopTest.getPrizeList().size());

        assertEquals(purchasedPrize,shopTest.getCurrentCasino().getInventory().get(0));

        assertEquals(casinoTest.getPlayerBalance(),oldBalance - purchasedPrize.getValue());
    }

    @Test
    void testSellPrize() {
        int oldBalance = casinoTest.getPlayerBalance();
        shopTest.generatePrizes();
        assertEquals(9,shopTest.getPrizeList().size());

        assertTrue(shopTest.buyPrize(1));

        Prize purchasedPrize = casinoTest.getInventory().get(0);
        assertEquals(8,shopTest.getPrizeList().size());

        assertEquals(purchasedPrize,shopTest.getCurrentCasino().getInventory().get(0));

        assertEquals(casinoTest.getPlayerBalance(),oldBalance - purchasedPrize.getValue());

        shopTest.sellPrize(purchasedPrize);
        assertEquals(oldBalance, casinoTest.getPlayerBalance());
        assertFalse(casinoTest.getInventory().contains(purchasedPrize));
    }
}
