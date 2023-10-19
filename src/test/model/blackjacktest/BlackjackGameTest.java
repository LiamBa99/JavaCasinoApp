package model.blackjacktest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import model.blackjack.*;
import model.casino.*;

import java.util.List;

class BlackjackGameTest {

    private BlackjackGame blackjackTestGame;
    private BlackjackGame blackjackTestGame2;
    private BlackjackGame blackjackTestGame3;
    private CardDeck testCardDeck;
    private Casino casinoTest;

    @BeforeEach
    void runBefore() {
        casinoTest = new Casino(1000);
        blackjackTestGame = new BlackjackGame(4, casinoTest);
        blackjackTestGame2 = new BlackjackGame(0,casinoTest);
        blackjackTestGame3 = new BlackjackGame(1,casinoTest);
        testCardDeck = new CardDeck();
    }

    @Test
    void constructorTest(){
        // case with standard construction
        assertEquals(4,blackjackTestGame.getNumOfDecks());
        assertEquals(casinoTest,blackjackTestGame.getCurrentCasino());

        // minimum case
        assertEquals(0,blackjackTestGame2.getNumOfDecks());
        assertEquals(casinoTest,blackjackTestGame2.getCurrentCasino());
    }

    @Test
    void playARoundTest(){

    }

    @Test
    void checkEnoughMoneyTest(){
        // true case
        assertTrue(blackjackTestGame.checkEnoughMoney(500));

        // false case
        assertFalse(blackjackTestGame.checkEnoughMoney(1001));
    }


    @Test
    void getDealerDecks() {
        // base case
        for (int i = 0; i < testCardDeck.getCardDeck().size(); i++) {
            assertEquals(blackjackTestGame3.getDealerDecks().get(i).getCardValue(),
                    testCardDeck.getCardDeck().get(i).getCardValue());
        }

        // multiple deck case
        for (int i = 0; i < blackjackTestGame.getNumOfDecks(); i++) {
            for (int j = 0; j < testCardDeck.getCardDeck().size(); j++) {
                assertEquals(blackjackTestGame.getDealerDecks().get(j + (13 * i)).getCardValue()
                        ,testCardDeck.getCardDeck().get(j).getCardValue());
            }
        }
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
        assertEquals(4, blackjackTestGame.getNumOfDecks());
        // test the minimum case
        assertEquals(0,blackjackTestGame2.getNumOfDecks());
    }

    @Test
    void setNumOfDecksTest() {
        // set num of decks
        blackjackTestGame.setNumOfDecks(3);
        // check the num of decks corresponds correctly
        assertEquals(3,blackjackTestGame.getNumOfDecks());

        // check it works with zero
        blackjackTestGame.setNumOfDecks(0);

        // check the num of decks corresponds correctly
        assertEquals(0,blackjackTestGame.getNumOfDecks());
    }

    @Test
    void setNumOfCardsTest() {
        // set the num of cards
        blackjackTestGame.setNumOfCards(52);
        // check the num of cards corresponds correctly
        assertEquals(52, blackjackTestGame.getNumOfCards());

        // check it works with zero
        blackjackTestGame.setNumOfCards(0);
        // check the num of decks corresponds correctly
        assertEquals(0, blackjackTestGame.getNumOfCards());
    }

    @Test
    void setDealerDecksTest() {
        // set the dealer deck
        blackjackTestGame.setDealerDecks(testCardDeck.getCardDeck());
        // check the card deck is equal
        List<Card> cardList = blackjackTestGame.getDealerDecks();
        for (int i = 0; i < cardList.size(); i++) {
            assertEquals(testCardDeck.getCardDeck().get(i),cardList.get(i));
        }

    }

    @Test
    void setCasinoTest() {
        // create a test casino
        Casino casino = new Casino(45);

        // set the casino
        blackjackTestGame.setCasino(casino);

        // check the casino corresponds correctly
        assertEquals(casino, blackjackTestGame.getCasino());
    }

}