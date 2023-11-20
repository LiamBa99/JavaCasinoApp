package model.blackjacktest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import model.blackjack.*;

class CardDeckTest {

    private CardDeck testCardDeck;

    @BeforeEach
    void runBefore() {
        testCardDeck = new CardDeck();
    }

    @Test
    void testConstructor(){
        List<Card> testCardList = testCardDeck.getCardDeck();
        int i = 1;
        int j = 0;
        String[] suitList = {"Hearts","Diamonds","Clubs","Spades"};

        for (Card card: testCardList) {
            String curSuit = suitList[j];
            if (i % 13 == 11 || i % 13 == 12 || i % 13 == 0) {
                assertEquals(10,card.getCardValue());
                assertEquals(curSuit,card.getSuit());
            } else if (i == 1 || i % 14 == 0) {
                assertEquals(11, card.getCardValue());
                assertEquals(curSuit,card.getSuit());
            }
            else {
                assertEquals(i,card.getCardValue());
                assertEquals(curSuit,card.getSuit());
            }

            if (i == 13) {
                i = 1;
                j++;
            } else {
                i++;
            }
        }
    }

    @Test
    void testCardImage() {
        List<Card> testDeck = testCardDeck.getCardDeck();
        Card card = testDeck.get(0);
        assertEquals("./data/images/ace_of_hearts.png",card.getCardImage());
    }
}
