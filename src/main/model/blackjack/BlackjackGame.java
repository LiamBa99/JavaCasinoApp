package model.blackjack;

import model.casino.*;
import java.util.*;

// represents a game of blackjack with a variable amount of card decks
public class BlackjackGame {
    private List<Card> dealerDecks; // represents the variable amount of card decks the dealer randomly gets from
    private int numOfPlayers; // represents the amount of players playing blackjack
    private Casino casino; // represents the casino the player is currently playing at
    private int currentBet;
    private int numOfDecks;

    // REQUIRES: casino is not null
    // EFFECTS:
    // creates a number of card decks according to numOfDecks and adds them to the dealerDeck
    // sets number of players to numOfPlayers
    // sets casino to casino
    public BlackjackGame(int numOfDecks, int numOfPlayers, Casino casino) {

    }

    // TODO: move to CasinoApp
    // REQUIRES: betSize > 0
    // MODIFIES: this, BlackJackRound
    // EFFECTS: if the player has enough money to play the selected bet size for the round
    // passes the dealerDecks as an argument create a BlackJackRound instance
    public void playARound(int betSize) {

    }

    // EFFECTS: checks if the player has enough money to play the round
    public boolean checkEnoughMoney(int attemptedBet) {
        return false;
    }


    // EFFECTS: returns the number of players in the game
    public int getNumOfPlayers() {
        return 0;
    }

    // EFFECTS: returns the list of cards in the dealer's deck
    public List<Card> getDealerDecks() {
        return null;
    }

    // EFFECTS: returns the player's bet
    public int getCurrentBet() {
        return 0;
    }

    // EFFECTS: returns the casino the player is currently in
    public Casino getCurrentCasino() {
        return null;
    }

    // EFFECTS: returns the number of decks
    public int getNumOfDecks() {
        return 0;
    }

    // REQUIRES: newBet > 0
    // MODIFIES: this
    // EFFECTS: sets the players new current bet
    public void setCurrentBet(int newBet) {

    }

}
