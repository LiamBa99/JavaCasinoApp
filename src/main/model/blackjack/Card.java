package model.blackjack;

// represents a playing card that excludes jokers, having a value 1 - 13 and a suit.
// colour of the card is derived from the suit
public class Card {
    private int cardValue; // tracks the value of the card
    private String suit; // tracks the suit of the card

    // REQUIRES: cardValue is in the range 1 - 13 and suit is either "Spade" "Club" "Diamond" or "Heart
    // EFFECTS: cardValue is set to cardValue, suit is set to suit
    public Card(int cardValue, String suit) {
        //stub
    }

    // EFFECTS: returns the value of the card
    public int getCardValue() {
        return 0; //stub
    }

    // EFFECTS: return the suit of the card
    public String getSuit() {
        return null;
    }
}
