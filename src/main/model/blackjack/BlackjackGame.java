package model.blackjack;

import model.casino.*;
import persistence.Writable;

import org.json.*;

import java.util.*;

// represents a game of blackjack with a variable amount of card decks
public class BlackjackGame implements Writable {
    private List<Card> dealerDecks; // represents the variable amount of card decks the dealer randomly gets from
    private Casino casino; // represents the casino the player is currently playing at
    private int currentBet; // represents the players current bet
    private int numOfDecks; // represents the number of decks in play
    private int numOfCards; // represents the number of cards in the deck

    // REQUIRES: casino is not null
    // EFFECTS: creates a number of card decks according to numOfDecks and adds them to the dealerDeck
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

    // EFFECTS: returns this as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bet",currentBet);
        json.put("numDecks",numOfDecks);
        json.put("numCards",numOfCards);
        json.put("dealerDeck",dealerDecksToJson());

        return json;
    }

    // EFFECTS: returns the dealer decks as a json object
    public JSONArray dealerDecksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card card : dealerDecks) {
            jsonArray.put(card.toJson());
        }

        return jsonArray;
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

    // EFFECTS: this, CardDeck
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

    // MODIFIES: this
    // EFFECTS: set the number of decks
    public void setNumOfDecks(int numOfDecks) {
        this.numOfDecks = numOfDecks;
    }

    // MODIFIES: this
    // EFFECTS: set the number of cards
    public void setNumOfCards(int numOfCards) {
        this.numOfCards = numOfCards;
    }

    // MODIFIES: this
    // EFFECTS: sets the dealerdeck
    public void setDealerDecks(List<Card> cardList) {
        dealerDecks = cardList;
    }

    // MODIFIES: this
    // EFFECTS: set the casino
    public void setCasino(Casino casino) {
        this.casino = casino;
    }

    // EFFECTS: gets the num of cards
    public int getNumOfCards() {
        return numOfCards;
    }

    // EFFECTS: gets the casino
    public Casino getCasino() {
        return casino;
    }
}
