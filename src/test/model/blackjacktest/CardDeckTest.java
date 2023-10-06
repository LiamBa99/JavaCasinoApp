package model.blackjacktest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import model.blackjack.*;
import model.casino.*;


class CardDeckTest {

    private CardDeck testCardDeck;
    private List<Card> testCardList;

    @BeforeEach
    void runBefore() {
        testCardDeck = new CardDeck();
    }

    @Test
    void testConstructor(){
        testCardList = testCardDeck.getCardDeck();
        int i = 1;
        int j = 0;
        String[] suitList = {"Hearts","Diamonds","Clubs","Spades"};

        for (Card card: testCardList) {
            String curSuit = suitList[j];
            assertEquals(i,card.getCardValue());
            assertEquals(curSuit,card.getSuit());
            if (i == 13) {
                i = 1;
                j++;
            } else {
                i++;
            }
        }
    }
}
