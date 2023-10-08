package model.roulettetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.roulette.*;

public class RouletteTest {

    private RouletteRound testRoulette1;
    private RouletteRound testRoulette2;
    private RouletteRound testRoulette3;
    private int[] testPlayerSelection1;
    private int[] testPlayerSelection2;
    private String[] testColourSelection;
    private String[] testEvenSelection;
    private String[] testNoSelection;
    private int testPlayerBet;
    private final int[] rouletteBoard = {0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
            16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};


    @BeforeEach
    void runBefore() {
        testPlayerSelection1 = new int[] {0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
        testPlayerSelection2 = new int[] {1};
        testColourSelection = new String[] {"Red"};
        testEvenSelection = new String[] {"Even"};
        testNoSelection = new String[] {};
        testPlayerBet = 5;
        testRoulette1 = new RouletteRound(testPlayerSelection1,testPlayerBet,testColourSelection);
        testRoulette2 = new RouletteRound(testPlayerSelection2,testPlayerBet,testNoSelection);
        testRoulette3 = new RouletteRound(testPlayerSelection2,testPlayerBet,testEvenSelection);
    }

    @Test
    void testConstructor() {
        assertEquals(testPlayerBet,testRoulette1.getPlayerBet());
        assertEquals(testPlayerSelection1,testRoulette1.getPlayerSelection());
        assertEquals(rouletteBoard,testRoulette1.getRouletteBoard());
        assertEquals(testColourSelection,testRoulette1.getPlayerColourEvenSelection());
    }

    @Test
    void testCheckWin() {
        // test case where they must win
        assertTrue(testRoulette1.checkWin());

        // test case with low odds to win
        int winCounter = 0;
        int loseCounter = 0;

        for (int i = 0; i < 20; i++) {
            if(testRoulette2.checkWin()) {
                winCounter++;
            } else {
                loseCounter++;
            }
        }

        assertTrue(loseCounter > winCounter);

        // test case where they have close to 50% chance to win
        winCounter = 0;
        loseCounter = 0;

        for (int i = 0; i < 30; i++) {
            if(testRoulette3.checkWin()) {
                winCounter++;
            } else {
                loseCounter++;
            }
        }

        int upperRange = loseCounter + 5;
        int lowerRange = loseCounter - 5;

        assertTrue((lowerRange < winCounter) && (upperRange > winCounter));
    }

}
