package model.shoptest;

import model.prizeshop.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrizeTest {

    private Prize testPrize;
    private int[] colour;

    @BeforeEach
    void runBefore() {
        colour = new int[] {255,0,255};
        testPrize = new Prize(50,"Elephant", colour);
    }

    @Test
    void testConstructor() {
        assertEquals(50,testPrize.getValue());
        assertEquals("Elephant",testPrize.getAnimalType());
        assertEquals(colour,testPrize.getColour());
    }
}
