package model.blackjack;

import model.CasinoGame;
import model.casino.*;
import java.util.*;

// represents a game of blackjack with a variable amount of card decks
public class BlackjackGame implements CasinoGame {
    private final List<Card> dealerDecks; // represents the variable amount of card decks the dealer randomly gets from
    private final Casino casino; // represents the casino the player is currently playing at
    private int currentBet; // represents the players current bet
    private final int numOfDecks; // represents the number of decks in play
    private int numOfCards; // represents the number of cards in the deck

    // REQUIRES: casino is not null
    // EFFECTS:
    // creates a number of card decks according to numOfDecks and adds them to the dealerDeck
    // sets casino to casino
    public BlackjackGame(int numOfDecks, Casino casino) {
        this.numOfDecks = numOfDecks;
        dealerDecks = new ArrayList<>();
        CardDeck cards = new CardDeck();
        for (int i = 0; i < numOfDecks; i++) {
            for (int j = 0; j < 52; j++) {
                dealerDecks.add(cards.getCardDeck().get(j));
            }
        }
        this.numOfCards = 52 * numOfDecks;
        this.casino = casino;
    }

    // REQUIRES: newBet > 0
    // MODIFIES: this
    // EFFECTS: sets the players new current bet
    public void setCurrentBet(int newBet) {
        this.currentBet = newBet;
    }

    // EFFECTS: checks if the player has enough money to play the round
    public boolean checkEnoughMoney(int attemptedBet) {
        return (casino.getPlayerBalance() > attemptedBet);
    }

    // EFFECTS: returns the list of cards in the dealer's deck
    public List<Card> getDealerDecks() {
        return dealerDecks;
    }

    // EFFECTS: returns and removes a random card in the dealer's deck
    public Card removeRandomCard() {
        Random random = new Random();
        int randomIndex = random.nextInt(numOfCards);
        Card returnCard = dealerDecks.remove(randomIndex);
        this.numOfCards--;
        return returnCard;
    }

    // EFFECTS: returns the player's bet
    public int getCurrentBet() {
        return currentBet;
    }

    // EFFECTS: returns the casino the player is currently in
    public Casino getCurrentCasino() {
        return casino;
    }

    // EFFECTS: returns the number of decks
    public int getNumOfDecks() {
        return numOfDecks;
    }
}
