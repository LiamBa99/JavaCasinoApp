package model.blackjack;

import java.util.*;

public class BlackjackRound {

    private List<Card> playerHand;
    private List<Card> dealerHand;
    private final BlackjackGame blackjack;
    private int playerCardValue;
    private int dealerCardValue;

    // EFFECTS: constructs a round of blackjack with player and dealer card value set to 0
    public BlackjackRound(BlackjackGame blackjack) {
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


    // EFFECTS: deals out the first round of cards
    public void dealFirstCards() {
        Card cardToAdd = dealACard(false);
        playerHand.add(cardToAdd);
        playerCardValue += cardToAdd.getCardValue();

        cardToAdd = dealACard(true);
        dealerHand.add(cardToAdd);
        dealerCardValue += cardToAdd.getCardValue();

        cardToAdd = dealACard(false);
        playerHand.add(cardToAdd);
        playerCardValue += cardToAdd.getCardValue();
    }

    // EFFECTS: deals cards to the dealer until they are at a hard 17 or bust
    public void dealUntilComplete() {
        while (dealerCardValue < 17) {
            Card cardToAdd = dealACard(true);
            dealerHand.add(cardToAdd);
            dealerCardValue += cardToAdd.getCardValue();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets this.win to true if the user won the round, false if the dealer won
    public boolean checkWin() {
        if (dealerCardValue > 21) {
            return true;
        } else if (playerCardValue > dealerCardValue && playerCardValue < 21) {
            return true;
        } else if (dealerCardValue > playerCardValue && dealerCardValue < 21) {
            return false;
        } else if (playerCardValue > 21) {
            return false;
        }
        return false;
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

}
