package model.blackjack;

import java.util.*;

public class BlackjackRound {

    private boolean win; // represents whether the player has won the round
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private int playerCardValue;
    private int dealerCardValue;

    // EFFECTS: constructs a round of blackjack with win set to false,
    // and player and dealer card value set to 0
    public BlackjackRound() {

    }

    // MODIFIES: this
    // EFFECTS: removes a card from the dealer's deck at random and returns it
    public Card chooseACard() {
        return null;
    }


    // EFFECTS: deals out the first round of cards
    public void dealFirstCards() {

    }

    // EFFECTS: prints the card randomly removed from the dealer list either on the dealer side or the player side
    public void showNextCard(boolean dealer) {

    }

    // EFFECTS: deals cards to the dealer until they are at a hard 17 or bust
    public void dealUntilComplete() {

    }

    // MODIFIES: this
    // EFFECTS: sets this.win to true if the user won the round, false if the dealer won
    public void checkWin() {

    }

    // EFFECTS: returns the player's current hand of cards
    public List<Card> getPlayerHand() {
        return null;
    }

    // EFFECTS: returns the player's current hand of cards
    public List<Card> getDealerHand() {
        return null;
    }

    // EFFECTS: returns the player's current card value
    public int getPlayerCardValue() {
        return 0;
    }

    // EFFECTS: returns the dealer's current card value
    public int getDealerCardValue() {
        return 0;
    }
}
