package model.blackjacktest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import model.blackjack.*;
import model.casino.*;

public class BlackjackRoundTest {

    BlackjackRound testRound;
    BlackjackGame testGame;
    Casino testCasino;

    @BeforeEach
    void runBefore() {
        testCasino = new Casino(10000);
        testGame = new BlackjackGame(4,testCasino);
        testRound = new BlackjackRound(testGame);
    }

    @Test
    void testConstructor() {
        assertTrue(testRound.getPlayerHand().isEmpty());
        assertTrue(testRound.getDealerHand().isEmpty());
        assertEquals(0,testRound.getPlayerCardValue());
        assertEquals(0,testRound.getDealerCardValue());
    }

    @Test
    void dealACardTest() {
        // dealer case
        testRound.dealACard(true);
        assertEquals(1,testRound.getDealerHand().size());
        assertEquals(0, testRound.getPlayerHand().size());

        // player case
        testRound.dealACard(false);
        assertEquals(1,testRound.getPlayerHand().size());
        assertEquals(1,testRound.getDealerHand().size());
    }

    @Test
    void dealFirstCardsTest() {
        testRound.dealFirstCards();
        assertEquals(2,testRound.getPlayerHand().size());
        assertEquals(1,testRound.getDealerHand().size());
    }

    @Test
    void dealUntilCompleteTest() {
        testRound.dealUntilComplete();
        assertTrue(testRound.getDealerCardValue() >= 17);
    }

    @Test
    void checkWinTest() {
        // dealer busts
        testRound.setCardValues(22,16);
        assertTrue(testRound.checkWin());

        // player is closer to 21 without going over
        testRound.setCardValues(17,18);
        assertTrue(testRound.checkWin());

        // dealer is closer to 21 without going over
        testRound.setCardValues(19,16);
        assertFalse(testRound.checkWin());

        // player busts
        testRound.setCardValues(17,22);
        assertFalse(testRound.checkWin());
    }
}
