package model.blackjacktest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import model.blackjack.*;
import model.casino.*;

class BlackjackGameTest {

    private BlackjackGame blackjackTestGame;
    private BlackjackGame blackjackTestGame2;
    private CardDeck testCardDeck;
    private Casino casinoTest;

    @BeforeEach
    void runBefore() {
        casinoTest = new Casino(1000);
        blackjackTestGame = new BlackjackGame(4, casinoTest);
        blackjackTestGame2 = new BlackjackGame(0,casinoTest);
        testCardDeck = new CardDeck();
    }

    @Test
    void constructorTest(){
        // case with standard construction
        assertEquals(1,blackjackTestGame.getNumOfPlayers());
        assertEquals(4,blackjackTestGame.getNumOfDecks());
        assertEquals(casinoTest,blackjackTestGame.getCurrentCasino());

        // minimum case
        assertEquals(0,blackjackTestGame2.getNumOfPlayers());
        assertEquals(0,blackjackTestGame2.getNumOfDecks());
        assertEquals(casinoTest,blackjackTestGame2.getCurrentCasino());
    }

    @Test
    void playARoundTest(){

    }

    @Test
    void checkEnoughMoneyTest(){
        // true case
        assertEquals(true,blackjackTestGame.checkEnoughMoney(500));

        // false case
        assertEquals(false,blackjackTestGame.checkEnoughMoney(1001));
    }

    @Test
    void getNumOfPlayersTest() {
        // base case
        assertEquals(1, blackjackTestGame.getNumOfPlayers());

        // case with zero players
        assertEquals(0,blackjackTestGame2.getNumOfPlayers());
    }

    @Test
    void getDealerDecks() {
        // base case
        assertEquals(testCardDeck.getCardDeck(),blackjackTestGame.getDealerDecks());
    }

    @Test
    void getCurrentBetTest() {
        // test the normal case
        blackjackTestGame.setCurrentBet(5);
        assertEquals(5,blackjackTestGame.getCurrentBet());

        // test the minimum case
        blackjackTestGame.setCurrentBet(1);
        assertEquals(1,blackjackTestGame.getCurrentBet());

        // test a high case
        blackjackTestGame.setCurrentBet(500000);
        assertEquals(500000,blackjackTestGame.getCurrentBet());
    }

    @Test
    void getCurrentCasinoTest() {
        // test the normal case
        assertEquals(casinoTest, blackjackTestGame.getCurrentCasino());
    }

    @Test
    void getNumOfDecksTest() {
        // test the normal case
        assertEquals(4, blackjackTestGame.getDealerDecks());
        // test the minimum case
        assertEquals(0,blackjackTestGame2.getDealerDecks());
    }

}