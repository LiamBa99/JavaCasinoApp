package model.blackjack;

import java.util.*;

// represents a set of 52 cards
public class CardDeck {

    private final List<Card> cardDeck; // tracks the list of cards remaining

    // EFFECTS: adds 52 unique cards to cardDeck
    public CardDeck() {
        this.cardDeck = createUniqueCards();
    }

    // MODIFIES: this
    // EFFECTS: generates a list of unique cards, starting with ace of spades and ending with king of hearts
    public List<Card> createUniqueCards() {
        String[] suitList = {"Hearts","Diamonds","Clubs","Spades"};
        List<Card> fullDeck = new ArrayList<>();
        int suitIndex = 0;
        int cardValue = 1;
        int faceValue = 10;
        String currentSuit = suitList[suitIndex];

        for (int i = 1; i <= 52; i++) {
            if (i % 13 == 11 || i % 13 == 12 || i % 13 == 0) {
                fullDeck.add(new Card(faceValue, currentSuit));
            } else if ((i - 1) % 13 == 0) {
                fullDeck.add(new Card(11, currentSuit));
            } else {
                fullDeck.add(new Card(cardValue, currentSuit));
            }
            cardValue++;
            if (i % 13 == 0 && i != 52) {
                suitIndex++;
                currentSuit = suitList[suitIndex];
                cardValue = 1;
            }
        }

        return fullDeck;
    }

    public List<Card> getCardDeck() {
        return cardDeck;
    }
}
