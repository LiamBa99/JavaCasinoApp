package model.blackjack;

import java.util.*;

public class BlackjackRound {

    private final List<Card> playerHand; // represents the players hand
    private final List<Card> dealerHand; // represents the dealers hand
    private final BlackjackGame blackjack; // represents the overall game of blackjack
    private int playerCardValue; // represents the value of the player's cards
    private int dealerCardValue; // represents the value of the dealer's cards

    // EFFECTS: constructs a round of blackjack with player and dealer card value set to 0
    public BlackjackRound(BlackjackGame blackjack) {
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        this.blackjack = blackjack;
        playerCardValue = 0;
        dealerCardValue = 0;
    }

    // MODIFIES: this
    // EFFECTS: removes a card from the dealer's deck at random and returns it
    public Card dealACard(boolean dealer) {
        Card cardToDeal = blackjack.removeRandomCard();

        if (dealer) {
            dealerHand.add(cardToDeal);
            dealerCardValue += cardToDeal.getCardValue();
        } else {
            playerHand.add(cardToDeal);
            playerCardValue += cardToDeal.getCardValue();
        }
        return cardToDeal;
    }

    // MODIFIES: this
    // EFFECTS: deals out the first round of cards
    public void dealFirstCards() {
        dealACard(false);
        dealACard(true);
        dealACard(false);

    }

    // MODIFIES: this
    // EFFECTS: deals cards to the dealer until they are at a hard 17 or bust
    public void dealUntilComplete() {
        while (dealerCardValue < 17) {
            dealACard(true);
        }
    }

    // EFFECTS: returns true if the user won the round, false if the dealer won
    public boolean checkWin() {
        boolean win = false;

        if (dealerCardValue > 21) {
            win = true;
        } else if (playerCardValue > dealerCardValue && playerCardValue <= 21) {
            win = true;
        } else if (dealerCardValue > playerCardValue && dealerCardValue < 21) {
            win = false;
        } else if (playerCardValue > 21) {
            win = false;
        }
        return win;
    }

    // EFFECTS: returns the player's current hand of cards
    public List<Card> getPlayerHand() {
        return playerHand;
    }

    // EFFECTS: returns the player's current hand of cards
    public List<Card> getDealerHand() {
        return dealerHand;
    }

    // EFFECTS: returns the player's current card value
    public int getPlayerCardValue() {
        return playerCardValue;
    }

    // EFFECTS: returns the dealer's current card value
    public int getDealerCardValue() {
        return dealerCardValue;
    }

    // MODIFIES: this
    // EFFECTS: sets the card values of the dealer and player
    public void setCardValues(int dealer, int player) {
        dealerCardValue = dealer;
        playerCardValue = player;
    }
}
