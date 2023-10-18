package model.shoptest;

import model.prizeshop.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrizeTest {

    private Prize testPrize;

    @BeforeEach
    void runBefore() {
        testPrize = new Prize(50,"Elephant",0);
    }

    @Test
    void testConstructor() {
        assertEquals(50,testPrize.getValue());
        assertEquals("Elephant",testPrize.getAnimalType());
    }
}
