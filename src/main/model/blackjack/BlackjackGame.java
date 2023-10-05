package model.blackjack;

import model.casino.*;
import java.util.*;

// represents a game of blackjack with a variable amount of card decks
public class BlackjackGame {
    private List<Card> dealerDecks; // represents the variable amount of card decks the dealer randomly gets from
    private int numOfPlayers; // represents the amount of players playing blackjack
    private Casino casino; // represents the casino the player is currently playing at
    private int currentBet;

    // REQUIRES: casino is not null
    // EFFECTS:
    // creates a number of card decks according to numOfDecks and adds them to the dealerDeck
    // sets number of players to numOfPlayers
    // sets casino to casino
    public BlackjackGame(int numOfDecks, int numOfPlayers, Casino casino) {

    }

    // REQUIRES: betSize > 0
    // MODIFIES: this, BlackJackRound
    // EFFECTS: if the player has enough money to play the selected bet size for the round
    // passes the dealerDecks as an argument create a BlackJackRound instance
    public void playARound(int betSize) {

    }

    // EFFECTS: checks if the player has enough money to play the round
    public boolean checkEnoughMoney() {
        return false;
    }

    // EFFECTS: asks the player how much they would like to bet for the next round
    // and sets that value for current bet
    public void getBetSize() {

    }


}
