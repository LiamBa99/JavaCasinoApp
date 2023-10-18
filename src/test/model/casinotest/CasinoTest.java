package model.casinotest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import model.casino.*;
import model.prizeshop.*;

public class CasinoTest {

    private Casino testCasino;
    private Prize testPrize;

    @BeforeEach
    void runBefore() {
        testCasino = new Casino(1000);
        testPrize = new Prize(500,"Elephant",0);
    }

    @Test
    void testConstructor(){
        assertEquals(1000,testCasino.getPlayerBalance());
    }

    // TODO: add in saving/testing
    @Test
    void testSaveCasinoSession() {

    }

    @Test
    void testAddPlayerBalance() {
        // base case
        testCasino.addPlayerBalance(500);
        assertEquals(1500,testCasino.getPlayerBalance());

        // adding zero
        testCasino.addPlayerBalance(0);
        assertEquals(1500,testCasino.getPlayerBalance());

        // adding a high number
        testCasino.addPlayerBalance(500000);
        assertEquals(501500,testCasino.getPlayerBalance());
    }

    @Test
    void testDeductPlayerBalance() {
        // base case
        assertTrue(testCasino.deductPlayerBalance(5));
        assertEquals(995,testCasino.getPlayerBalance());

        // deducting zero
        assertTrue(testCasino.deductPlayerBalance(0));
        assertEquals(995,testCasino.getPlayerBalance());

        // deducting greater than balance
        assertFalse(testCasino.deductPlayerBalance(1000));
        assertEquals(995,testCasino.getPlayerBalance());
    }

    @Test
    void testPrizeList(){
        // add a prize
        assertTrue(testCasino.addPrize(testPrize));

        // get the prizelist
        List<Prize> testPrizeList = testCasino.getInventory();

        // check the list contains the prize
        assertTrue(testPrizeList.contains(testPrize));

        // remove the prize
        assertTrue(testCasino.removePrize(testPrize));

        // get the list again
        testPrizeList = testCasino.getInventory();

        // check the list no longer contains the prize
        assertFalse(testPrizeList.contains(testPrize));

        // attempt to remove the prize again
        assertFalse(testCasino.removePrize(testPrize));
    }
}
