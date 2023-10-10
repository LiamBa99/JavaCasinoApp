package model.roulettetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.roulette.*;

import java.util.*;

public class RouletteTest {

    private RouletteRound testRoulette1;
    private RouletteRound testRoulette2;
    private RouletteRound testRoulette3;
    private RouletteRound testRoulette4;
    private int[] testPlayerSelection1;
    private List<String> testColourSelection;
    private List<String> testColourSelection2;
    private List<String> testColourSelection3;
    private int testPlayerBet;
    private final int[] rouletteBoard = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
            16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};


    @BeforeEach
    void runBefore() {
        testPlayerSelection1 = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
        int[] testPlayerSelection2 = new int[] {1};
        testColourSelection = new ArrayList<>();
        testColourSelection.add("red");
        testColourSelection2 = new ArrayList<>();
        testColourSelection2.add("black");
        testColourSelection3 = new ArrayList<>();
        testColourSelection3.add("red");
        List<String> testNoSelection = new ArrayList<>();
        testPlayerBet = 5;
        testRoulette1 = new RouletteRound(testPlayerSelection1,testPlayerBet,testColourSelection);
        testRoulette2 = new RouletteRound(testPlayerSelection2,testPlayerBet,testNoSelection);
        testRoulette3 = new RouletteRound(testPlayerSelection2,testPlayerBet,testColourSelection2);
        testRoulette4 = new RouletteRound(testPlayerSelection2,testPlayerBet,testColourSelection3);
    }

    @Test
    void testConstructor() {
        assertEquals(testPlayerBet,testRoulette1.getPlayerBet());
        assertEquals(testPlayerSelection1,testRoulette1.getPlayerSelection());
        for (int i = 0; i < rouletteBoard.length - 1; i++) {
            assertEquals(rouletteBoard[i], testRoulette1.getRouletteBoard()[i]);
        }
        assertEquals(testColourSelection,testRoulette1.getPlayerColourEvenSelection());
    }

    @Test
    void testCheckWin() {
        // test case where they must win
        assertTrue(testRoulette1.checkWin() >= 1);

        // test case with low odds to win
        int winCounter = 0;
        int loseCounter = 0;

        for (int i = 0; i < 20; i++) {
            int winWeight = testRoulette2.checkWin();
            if(winWeight == 1 || winWeight == 2) {
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
            int winWeight = testRoulette3.checkWin();
            if(winWeight == 1 || winWeight == 2) {
                winCounter++;
            } else {
                loseCounter++;
            }
        }

        int upperRange = loseCounter + 10;
        int lowerRange = loseCounter - 10;

        assertTrue((lowerRange < winCounter) && (upperRange > winCounter));

        // test case with 50% chance to win with red
        winCounter = 0;
        loseCounter = 0;

        for (int i = 0; i < 30; i++) {
            int winWeight = testRoulette4.checkWin();
            if(winWeight == 1 || winWeight == 2) {
                winCounter++;
            } else {
                loseCounter++;
            }
        }

        upperRange = loseCounter + 10;
        lowerRange = loseCounter - 10;

        assertTrue((lowerRange < winCounter) && (upperRange > winCounter));
    }

    @Test
    void testSelectNumber(){
        boolean found = false;
        int winningNumber = testRoulette1.selectNumber();
        for (int i : testRoulette1.getPlayerSelection()) {
            if (i == winningNumber) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }

}
